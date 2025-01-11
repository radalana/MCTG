package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.TradingRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.*;

public class TradingController extends Controller {
    //TODO idea req uire element
    public enum RequiredType {
        MONSTER,
        SPELL,
        ELF, WIZARD, DRAGON, GOBLIN, ORK, KRAKEN, KNIGHT;

        public static RequiredType fromString(String type) {
            if (type == null || type.isEmpty()) {
                return null;
            }
            return RequiredType.valueOf(type.toUpperCase());
        }
    }
    public Response createTradingDeal(String token, String requestBody){
        try (UnitOfWork unitOfWork = new UnitOfWork()){
            Map<String, String> data = this.getObjectMapper().readValue(requestBody, new TypeReference<Map<String,String>>(){});
            String dealId = data.get("Id");
            String cardToTradeId = data.get("CardToTrade");
            String type = data.get("Type");
            int minDamage = Integer.parseInt(data.get("MinimumDamage"));

            //check user
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }
            CardDao cardDao = new CardDao(unitOfWork);

            //check if card belongs to the user
            if (cardDao.getUserId(cardToTradeId) != user.getId()) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Only the owner of this card can initiate a trade.\" }\n");
            }

            //check if card is in deck
            if (cardDao.getIsInDeckFlag(cardToTradeId)) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Cards in deck are blocked for trading\" }\n");
            }
            Card cardToTrade = cardDao.findCardById(cardToTradeId);
            if (cardToTrade == null) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Card not found\" }\n");
            }
            //check type of required card
            RequiredType requiredType = RequiredType.fromString(type.toUpperCase());
            TradeOffer offer = new TradeOffer(dealId, cardToTrade, user, requiredType, minDamage);
            new TradingRepository(unitOfWork).save(offer);
            unitOfWork.commitTransaction();
            return new Response(HttpStatus.CREATED, "{ \"message\": \"Card with id: " + cardToTradeId + " was pushed into store\" }\n");
        }catch (JsonProcessingException e){
            return new Response(HttpStatus.BAD_REQUEST, INVALID_JSON_FORMAT);
        }catch (IllegalArgumentException e) {
            return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Invalid required type of card to trade\" }\n");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }

    }
    public Response listDeals(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }
            List<TradeOffer> offersList =  new TradingRepository(unitOfWork).findAll();
            List<String> offers = offersList.stream()
                    .map(offer -> String.format("Deal id: %s. User %s offers %s (%.1f damage) and wants \"%s with min %d damage\"\n",
                            offer.getId(), offer.getTrader().getLogin(), offer.getCard().getName(), offer.getCard().getDamage(), offer.getType(), offer.getMinDamage()))
                    .toList();

            String content = "The store is empty\n";
            if (!offers.isEmpty()) {
                content = offers + "\n";
            }
            return new Response(HttpStatus.OK, content);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }

    public Response deleteOffer(String token, String offerId) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }
            TradingRepository tradingRepository = new TradingRepository(unitOfWork);
            TradeOffer tradeOffer = tradingRepository.findById(offerId);
            if (tradeOffer == null) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deal with id: " + offerId + " was not found\" }\n");
            }

            if (tradeOffer.getTrader().getId() != user.getId()) {
                return new Response(HttpStatus.FORBIDDEN, "{ \"message\": \"Only the creator of the trade offer can delete it.\" }\n");
            }

            if (tradingRepository.delete(tradeOffer)) {
                unitOfWork.commitTransaction();
                return new Response(HttpStatus.NO_CONTENT, "");
            }

            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }

    public Response completeDeal(String token, String offerId, String body) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }

            TradingRepository tradingRepository = new TradingRepository(unitOfWork);
            TradeOffer tradeOffer = tradingRepository.findById(offerId);
            if (tradeOffer == null) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deal with id: " + offerId + " was not found\" }\n");
            }
            if (tradeOffer.isClosed()) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"The trade offer is already closed.}\n");
            }

            if (tradeOffer.getTrader().getId() == user.getId()) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Trading with yourself is not allowed.}\n");
            }

            CardDao cardDao = new CardDao(unitOfWork);
            String cardId = body;
            if (body.startsWith("\"") && body.endsWith("\"")) {
                cardId = body.substring(1, body.length() - 1); // Удаляем первую и последнюю кавычки
            }
            Card card = cardDao.findCardById(cardId);
            //check if the card meets the requirements
            RequiredType requiredType = tradeOffer.getType();
            if (!isValidType(requiredType, card)) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"The required card type for this trade is: " + requiredType + ". Your card type is: " + card.getClass().getSimpleName() + ".\" }\n");
            }

            if (card.getDamage() < tradeOffer.getMinDamage()) {
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Min damage for this trade: " + tradeOffer.getMinDamage() + ". Your card damage is: " + card.getDamage() + ".\" }\n");
            }


            tradingRepository.closeTradeOffer(tradeOffer);
            cardDao.updateOwnership(card, tradeOffer.getTrader());
            cardDao.updateOwnership(tradeOffer.getCard(), user);
            unitOfWork.commitTransaction();
            return new Response(HttpStatus.CREATED, "success");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidType(RequiredType requiredType, Card card) {
        //no requirements for card type
        if (requiredType == null) {
            return true;
        }

        return switch (requiredType) {
            case SPELL -> !card.isMonsterType();
            case MONSTER -> card.isMonsterType();
            default -> card.getClass().getSimpleName().equals(requiredType.toString());
        };
    }
}

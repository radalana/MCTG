package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.TradingRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

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

            //check type of required card
            RequiredType requiredType = RequiredType.fromString(type.toUpperCase());

            TradeOffer offer = new TradeOffer(dealId, cardToTradeId, user.getId(), requiredType, minDamage);
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
        return new Response(HttpStatus.NOT_IMPLEMENTED, "show deals not implemented");
    }

}

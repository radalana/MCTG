package at.fhtw.swen.mctg.services.deck;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import at.fhtw.swen.mctg.services.cards.CardService;
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.*;
import static at.fhtw.swen.mctg.model.Deck.EXACT_CARDS_REQUIRED;


public class DeckController extends Controller {
    private static final String DECK_ALREADY_EXISTS = "{ \"message\": \"Deck is already configured. You can configure your deck only once for each battle.\" }";
    private static final String CARDS_NOT_IN_STACK = "{ \"message\": \"Some selected cards do not belong to your stack. Please select valid cards.\" }";
    private final AuthenticationService authenticationService;

    public DeckController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    public Response listCardsFromDeck(Request request) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            if (request.getBody() != null) {
                return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_NOT_ALLOWED);
            }
            String token = request.getHeaderMap().getHeader("Authorization");
            token = authenticationService.extractToken(token);
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }
            int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            CardService cardService = new CardService(new CardDao(unitOfWork));
            List<Map<String, Object>> cardsAsMap = cardService.getCardsFromDeckAsMap(stackId);
            String json = new ObjectMapper().writeValueAsString(cardsAsMap);
            return new Response(HttpStatus.OK, json);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR,  INTERNAL_SERVER_ERROR );
        }
    }


    public Response configureDeck(Request request) {
        if (request.getBody() == null) {
            return new Response(HttpStatus.BAD_REQUEST, NO_CARDS_PROVIDED);
        }
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            String token = request.getHeaderMap().getHeader("Authorization");
            token = authenticationService.extractToken(token);
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }

            //get cards id from request
            List<String> cardsIdList = this.getObjectMapper().readValue(request.getBody(), new TypeReference<List<String>>() {
            });

            //check if 4 cards provided
            if (cardsIdList.size() != EXACT_CARDS_REQUIRED) {
                return new Response(HttpStatus.BAD_REQUEST, INVALID_NUMBER_OF_CARDS);
            }

            CardDao cardDao = new CardDao(unitOfWork);
            //Check if the provided cards exist
            if (!cardDao.areCardsWithIdExist(cardsIdList)) {
                return new Response(HttpStatus.BAD_REQUEST, CARD_NOT_FOUND);
            }

            //Check if the provided cards belong to the user
            int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            //TODO refactor areCardsInStackWithId имплементировать здесь? а запрос в дао, потому что там не должна выполняться логика на сравнение
            if (!cardDao.areCardsInStackWithId(cardsIdList, stackId)) { //cards are in stack, that belong to this user
                //TODO and show original from before:
                return new Response(HttpStatus.BAD_REQUEST, CARDS_NOT_IN_STACK);
            }

            //does user already has active cards (in deck)
            if (!isUsersDeckEmpty(stackId, cardDao)) {
                return new Response(HttpStatus.BAD_REQUEST, DECK_ALREADY_EXISTS);
            }

            addCardsInDeck(cardsIdList, cardDao);
            unitOfWork.commitTransaction();
            return new Response(HttpStatus.OK, "Deck is successfully configured");
        } catch (Exception e) {
            System.err.println("configureDeck: " + e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR,  INTERNAL_SERVER_ERROR );
        }
    }

    private boolean isUsersDeckEmpty(int userStackId, CardDao cardDao) throws DataAccessException {
        return cardDao.countCardsInDeck(userStackId) == 0;
    }

    private void addCardsInDeck(List<String> cards, CardDao cardDao) throws DataAccessException {
        cardDao.setDeckFlagForCards(cards);
    }
}

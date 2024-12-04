package at.fhtw.swen.mctg.services.deck;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import at.fhtw.swen.mctg.services.cards.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class DeckController extends Controller {
    public Response listCardsFromDeck(String token) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = new UserRepository(unitOfWork).findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, "You are not logged in");
            }
            int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            CardService cardService = new CardService(new CardDao(unitOfWork));
            List<Map<String, Object>> cardsAsMap = cardService.getCardsFromDeckAsMap(stackId);
            String json = new ObjectMapper().writeValueAsString(cardsAsMap);
            return new Response(HttpStatus.OK, json);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR,  "{ \"message\", \"An unexpected error occurred while processing your request. Please try again later.\"}" );
        }
    }
    public Response configureDeck(Request request) {
        if (request.getBody() == null) {
            return new Response(HttpStatus.BAD_REQUEST, "{ \"message\", \"No cards were provided.\"}");
        }
        return new Response(HttpStatus.NOT_IMPLEMENTED, "{ \"message\", \"configureDeck()\"}");
    }
}

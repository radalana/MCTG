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
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.*;

public class DeckController extends Controller {
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
        return new Response(HttpStatus.NOT_IMPLEMENTED, "{ \"message\", \"configureDeck()\"}");
    }
}

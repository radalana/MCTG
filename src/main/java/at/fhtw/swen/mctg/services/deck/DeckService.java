package at.fhtw.swen.mctg.services.deck;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INVALID_HTTP_METHOD;

public class DeckService implements Service {
    private final DeckController deckController;
    public DeckService(AuthenticationService authenticationService) {
        this.deckController = new DeckController(authenticationService);
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();

        //TODO extract logic in method of authentification service

        return switch (requestMethod) {
            case Method.GET -> deckController.listCardsFromDeck(request);
            case Method.PUT -> deckController.configureDeck(request);
            default -> new Response(
                    HttpStatus.BAD_REQUEST,
                    INVALID_HTTP_METHOD
            );
        };
    }
}

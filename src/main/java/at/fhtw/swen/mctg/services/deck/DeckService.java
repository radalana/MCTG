package at.fhtw.swen.mctg.services.deck;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

public class DeckService implements Service {
    private final DeckController deckController;
    private final AuthenticationService authenticationService;

    public DeckService(AuthenticationService authenticationService) {
        this.deckController = new DeckController();
        this.authenticationService = authenticationService;
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();

        //TODO extract logic in method of authentification service
        String token = request.getHeaderMap().getHeader("Authorization");
        token = authenticationService.extractToken(token);
        return switch (requestMethod) {
            case Method.GET -> deckController.listCardsFromDeck(token);
            case Method.PUT -> deckController.configureDeck(request);
            default -> new Response(
                    HttpStatus.BAD_REQUEST,
                    "{ \"message\", \"Bad request\"}"
            );
        };
    }
}

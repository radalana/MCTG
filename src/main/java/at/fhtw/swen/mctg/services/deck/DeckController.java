package at.fhtw.swen.mctg.services.deck;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

public class DeckController extends Controller {
    public Response listCardsFromDeck(String token) {
        return new Response(HttpStatus.NOT_IMPLEMENTED, "{ \"message\", \"listsCardsFromDeck()\"}");
    }
    public Response configureDeck(Request request) {
        if (request.getBody() == null) {
            return new Response(HttpStatus.BAD_REQUEST, "{ \"message\", \"No cards were provided.\"}");
        }
        return new Response(HttpStatus.NOT_IMPLEMENTED, "{ \"message\", \"configureDeck()\"}");
    }
}

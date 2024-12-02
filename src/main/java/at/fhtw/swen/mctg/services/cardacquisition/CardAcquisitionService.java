package at.fhtw.swen.mctg.services.cardacquisition;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

public class CardAcquisitionService implements Service {
    private final CardAcquisitionController controller;
    private final AuthenticationService authenticationService;
    public CardAcquisitionService(AuthenticationService authenticationService) {
        this.controller = new CardAcquisitionController();
        this.authenticationService = authenticationService;
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();

        String token = request.getHeaderMap().getHeader("Authorization");
        token = authenticationService.extractToken(token);
        if (requestMethod == Method.POST  && request.getBody() == null) {
            return this.controller.acquisiteCards(token);
        }
        return new Response(HttpStatus.BAD_REQUEST, "{ \"message\", \"Bad request for card acquisition\"}");
    }
}

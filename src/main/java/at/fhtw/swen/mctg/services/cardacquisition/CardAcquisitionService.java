package at.fhtw.swen.mctg.services.cardacquisition;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_NOT_ALLOWED;

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
        //System.out.println("Card Acquisition");
        //System.out.println("Path Name: " + request.getPathname());
        var pathParts = request.getPathParts();
        if (!pathParts.get(1).equals("packages")) {
            return new Response(HttpStatus.NOT_FOUND, "");
        }
        String token = request.getHeaderMap().getHeader("Authorization");
        try {
            token = authenticationService.extractToken(token);
            if (requestMethod == Method.POST  && request.getBody() == null) {
                return this.controller.acquisiteCards(token);
            }
            return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_NOT_ALLOWED);
        }catch(IllegalArgumentException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }
}

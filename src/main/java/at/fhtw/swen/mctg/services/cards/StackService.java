package at.fhtw.swen.mctg.services.cards;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_NOT_ALLOWED;

public class StackService implements Service {
    private final StackController stackController;
    private final AuthenticationService authenticationService;

    public StackService(AuthenticationService authenticationService) {
        this.stackController = new StackController();
        this.authenticationService = authenticationService;
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();

        String token = request.getHeaderMap().getHeader("Authorization");
        token = authenticationService.extractToken(token);
        if (requestMethod == Method.GET && request.getBody() == null) {
            return this.stackController.listAllCards(token);
        }
        return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_NOT_ALLOWED);
    }
}

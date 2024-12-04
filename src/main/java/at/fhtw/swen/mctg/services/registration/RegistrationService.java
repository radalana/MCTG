package at.fhtw.swen.mctg.services.registration;


import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_REQUIRED;

public class RegistrationService implements Service {

    private final RegistrationController regController;

    public RegistrationService(AuthenticationService authenticationService) {
        this.regController = new RegistrationController(authenticationService);
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.POST && request.getBody() != null) {
            return this.regController.signup(request);
        }
        return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_REQUIRED);
    }
}

package at.fhtw.swen.mctg.services.login;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_REQUIRED;

public class LoginService implements Service {

    private final LoginController loginController;

    public LoginService(AuthenticationService authenticationService) {
        this.loginController = new LoginController(authenticationService);
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.POST && request.getBody() != null) {
            return this.loginController.login(request);
        }
        return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_REQUIRED);
    }
}

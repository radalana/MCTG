package at.fhtw.swen.mctg.core.services.login;

import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;

public class LoginService implements Service {

    private final LoginController loginController;

    public LoginService() {
        this.loginController = new LoginController();
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.POST && request.getBody() != null) {
            return this.loginController.login(request);
        }
        return null;
    }
}

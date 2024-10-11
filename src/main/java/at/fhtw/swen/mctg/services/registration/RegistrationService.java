package at.fhtw.swen.mctg.services.registration;


import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;

public class RegistrationService implements Service {

    private final RegistrationController regController;

    public RegistrationService() {
        this.regController = new RegistrationController();
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.POST && request.getBody() != null) {
            return this.regController.signup(request);
        }
        return null;
    }
}

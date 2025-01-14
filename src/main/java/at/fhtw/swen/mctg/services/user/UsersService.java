package at.fhtw.swen.mctg.services.user;


import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.common.BaseService;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_REQUIRED;

public class UsersService extends BaseService {

    private final RegistrationController regController;
    private final UserController userController;

    public UsersService(AuthenticationService authenticationService) {
        this.regController = new RegistrationController(authenticationService);
        this.userController = new UserController();
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.POST && request.getBody() != null) {
            return this.regController.signup(request);
        }
        if (requestMethod == Method.GET && request.getBody() == null) {
            //TODO: getTokenFromRequest soll be in Service oder COntroller
            String token = getTokenFromRequest(request);;
            return this.userController.getUser(request, token);
        }
        if (requestMethod == Method.PUT && request.getBody() != null) {
            String token = getTokenFromRequest(request);
            return this.userController.editProfile(request, token);
        }
        return new Response(HttpStatus.BAD_REQUEST, "");
    }
}

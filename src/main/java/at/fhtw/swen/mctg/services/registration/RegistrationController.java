package at.fhtw.swen.mctg.services.registration;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.services.User;
import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import at.fhtw.swen.mctg.services.login.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

//session
public class RegistrationController extends Controller {
    private final AuthenticationService authService;
    public RegistrationController() {
        this.authService = new AuthenticationService();
    }
    public Response signup(Request request) {
        try {
            Map<String, String> signupData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<Map<String,String>>(){});
            String username = signupData.get(AuthenticationService.USERNAME);
            String password = signupData.get(AuthenticationService.PASSWORD);
            if (!isValid(username) || !isValid(password)) {
                return new Response(HttpStatus.BAD_REQUEST, "Invalid username/password");
            }
            if (authService.isUserExists(username)) {
                return new Response(
                        HttpStatus.CONFLICT,
                        "{ \"message\": Username is already taken. Please choose a different one.}"
                );
            }
            authService.signup(username, password);
            return new Response(
                    HttpStatus.CREATED,
                    "{ \"message\" : \"User successfully registered\" }"
            );
        }catch (JsonProcessingException | DataAccessException e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }
    private boolean isValid(String data) {
        return data != null && !data.isEmpty();
    }
}

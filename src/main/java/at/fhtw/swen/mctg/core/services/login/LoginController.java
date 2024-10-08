package at.fhtw.swen.mctg.core.services.login;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import at.fhtw.swen.mctg.persistence.dao.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

//session
public class LoginController extends Controller {
    private final AuthenticationService authService;
    public LoginController() {
        this.authService = new AuthenticationService();
    }
    public Response login(Request request) {
        try {
            Map<String, String> loginData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<Map<String,String>>(){});

            String token = authService.authenticate(loginData);
            if (token == null) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        "{ \"message\": \"Invalid username or password\" }"
                );
            }
            return new Response(
                    HttpStatus.OK,
                    "{ \"message\": { \"token\": \"" + token + "\", \"status\": \"login succeeded\" }}"
            );
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }

    }
    private boolean validateCredentials(Map<String, String> loginData) {
        //TODO: add validation
        return true;
    }
}

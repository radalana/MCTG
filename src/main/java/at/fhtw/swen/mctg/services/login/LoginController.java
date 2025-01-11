package at.fhtw.swen.mctg.services.login;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;

//session
public class LoginController extends Controller {

    private final AuthenticationService authService;
    public LoginController(AuthenticationService authService) {
        this.authService = authService;
    }
    public Response login(Request request) {
        try {
            Map<String, String> loginData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<Map<String,String>>(){});
            String username = loginData.get(AuthenticationService.USERNAME);
            String password = loginData.get(AuthenticationService.PASSWORD);

            if (!authService.isValid(username) || !authService.isValid(password)){
                return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Both login and password are required and cannot be empty.\" }"
                );
            }
            if (!authService.isUserExists(username)) {
                return new Response(HttpStatus.NOT_FOUND, "User with login " + username + " does not exist");
            }
            String token = authService.authenticateUser(username, password);
            return new Response(
                    HttpStatus.OK,
                    "{ \"message\": { \"token\": \"" + token + "\", \"status\": \"login succeeded\" }}"
            );
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            System.err.println("Json parsing error: " + e.getMessage());
            return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                    INTERNAL_SERVER_ERROR
            );
        }catch (DataAccessException e){
            System.out.println("Database error: " + e.getMessage());
            //e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    INTERNAL_SERVER_ERROR
            );
        }catch (IllegalArgumentException e) {
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    "{ \"message\" : " + e.getMessage() + " }"
            );
        }catch (Exception e) {
            System.err.println("Login controller: " + e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    INTERNAL_SERVER_ERROR
            );
        }

    }
}

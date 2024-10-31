package at.fhtw.swen.mctg.services.registration;

import at.fhtw.swen.mctg.services.User;
import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

//session
public class RegistrationController extends Controller {
    private final AuthenticationService authService;
    public RegistrationController(AuthenticationService authService) {
        this.authService = authService;
    }
    public Response signup(Request request) {
        try {
            Map<String, String> signupData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<Map<String,String>>(){});
            String username = signupData.get("username");
            String password = signupData.get("password");
            if (isUserExists(username)) {

            }
            if (newUser != null) {
                if (userRepository.save(newUser)){
                    return new Response(
                            HttpStatus.CREATED,
                            "{ \"message\": Registration successful!}"
                    );
                }//add exception if smth wrong by saving in db

            }
            return new Response(
                    HttpStatus.CONFLICT,
                    "{ \"message\": \"User is already exists\"}"
            );
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    private User createUser(Map<String,String> signUpData) {
        String username = signUpData.get("Username");
        String password = signUpData.get("Password");
        if (isValid(username) && isValid(password)) {
            if (!userRepository.isUserExists(username)){
                System.out.println("User with login: " + "doesn't exist");
                return new User(username, password);
            }
            return null;
        }
        return null;
    }

    private boolean isValid(String data) {
        return data != null && !data.isEmpty();
    }
}

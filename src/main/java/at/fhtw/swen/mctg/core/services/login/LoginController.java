package at.fhtw.swen.mctg.core.services.login;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

//session
public class LoginController extends Controller {
    public Response login(Request request) {
        try {
            //читает из body requesta
            String jsonBodyRequest = "{\n" +
                    "  \"login\": \"sveta@mail.com\",\n" +
                    "  \"password\": \"sveta\"\n" +
                    "}";
            Map<String, Object> loginData = this.getObjectMapper().readValue(jsonBodyRequest, new TypeReference<Map<String,Object>>(){});
            //check password and login
            return new Response(
                    HttpStatus.OK,
                    "{ message: \"token\": \"token\"}"

            );
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }

    }
}

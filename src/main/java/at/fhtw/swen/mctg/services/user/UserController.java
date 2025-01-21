package at.fhtw.swen.mctg.services.user;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.user.UserRepository;
import at.fhtw.swen.mctg.services.common.UserManager;
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.Map;
import java.util.Set;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.*;

public class UserController extends Controller {
    private final AuthenticationService authenticationService;
    UserController() {
        this.authenticationService = new AuthenticationService();
    }

    // /users/{username}
    public Response getUser(Request request, String token) {
        if (request.getPathParts().size() != 2) {
            return new Response(HttpStatus.BAD_REQUEST, "no parameter");
        }
        String username = request.getPathParts().get(1);
        if (!token.equals(authenticationService.generateToken(username))) {
            return new Response(HttpStatus.FORBIDDEN, "{ \"message\": \"Access denied\"}");
        }
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = UserManager.validateAndFetchUser(token, unitOfWork);
            String json = getObjectMapper().writeValueAsString(user);
            return new Response(HttpStatus.OK, json + "\n");
        }catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }

    public Response editProfile(Request request, String token) {
        if (request.getPathParts().size() != 2) {
            return new Response(HttpStatus.BAD_REQUEST, "no parameter\n");

        }
        String usernamePathParam = request.getPathParts().get(1);
        if (!token.equals(authenticationService.generateToken(usernamePathParam))) {
            return new Response(HttpStatus.FORBIDDEN, "{ \"message\": \"Access denied\"}");
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Map<String, String> editData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<Map<String, String>>() {
            });

            String username = editData.get("Name");
            String password = editData.get("Password");
            String bio = editData.get("Bio");
            String image = editData.get("Image");

            if (editData.containsKey("Coins") || editData.containsKey("Id")) {
                return new Response(HttpStatus.FORBIDDEN, "{ \"message\": \"Access denied: You are not allowed to edit this field.\"}" + "\n");
            }
            User user = UserManager.validateAndFetchUser(token, unitOfWork);
            user.setUsername(username);
            user.setPassword(password);
            user.setBio(bio);
            user.setImage(image);
            new UserRepository(unitOfWork).update(user);
            unitOfWork.commitTransaction();
            return new Response(HttpStatus.OK, "{ \"message\": \"Profile successfully updated\"}" + "\n");
        }catch(JsonMappingException e) {
            return new Response(HttpStatus.BAD_REQUEST, INVALID_JSON_FORMAT);
        }catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }
}

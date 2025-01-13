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

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;

public class UserController extends Controller {
    // /users/{username}
    public Response getUser(Request request, String token) {
        if (request.getPathParts().size() != 2) {
            return new Response(HttpStatus.BAD_REQUEST, "no parameter");
        }
        String username = request.getPathParts().get(1);
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = UserManager.validateAndFetchUser(token, unitOfWork);
            if (!username.equals(user.getUsername())) {
                return new Response(HttpStatus.FORBIDDEN, "");
            }
            String json = getObjectMapper().writeValueAsString(user);
            return new Response(HttpStatus.OK, json + "\n");
        }catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }
}

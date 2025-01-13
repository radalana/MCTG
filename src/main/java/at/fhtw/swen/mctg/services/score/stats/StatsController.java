package at.fhtw.swen.mctg.services.score.stats;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Stats;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.user.StatsRepository;
import at.fhtw.swen.mctg.services.common.UserManager;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;

public class StatsController extends Controller {
    public Response getStats(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = UserManager.validateAndFetchUser(token, unitOfWork);
            Stats stats = new StatsRepository(unitOfWork).findStats(user.getId());
            String json = getObjectMapper().writeValueAsString(stats);
            return new Response(HttpStatus.OK, json + "\n");
        }catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
        }catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }

    }
}

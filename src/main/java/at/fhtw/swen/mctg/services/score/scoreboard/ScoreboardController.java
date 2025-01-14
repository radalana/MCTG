package at.fhtw.swen.mctg.services.score.scoreboard;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Scoreboard;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.ScoreboardRepository;
import at.fhtw.swen.mctg.services.common.UserManager;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;

public class ScoreboardController extends Controller {
    public Response show(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            UserManager.validateAndFetchUser(token, unitOfWork);
            Scoreboard scoreboard = new ScoreboardRepository(unitOfWork).find();
            String json = getObjectMapper().writeValueAsString(scoreboard);
            return new Response(HttpStatus.OK, json + "\n");
        }catch (UserNotFoundException e) {
            return new Response(HttpStatus.FORBIDDEN, "{ \"message\": \"You must be logged in to perform this action.\" }" + "\n");
        }catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }
}

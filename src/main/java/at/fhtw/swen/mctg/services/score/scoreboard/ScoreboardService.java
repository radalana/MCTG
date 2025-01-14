package at.fhtw.swen.mctg.services.score.scoreboard;

import at.fhtw.swen.mctg.exceptions.MissingTokenException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.common.BaseService;

public class ScoreboardService extends BaseService {
    private final ScoreboardController scoreboardController;
    public ScoreboardService() {
        this.scoreboardController = new ScoreboardController();
    }

    @Override
    public Response handleRequest(Request request) {
        try{
            Method requestMethod = request.getMethod();
            if (requestMethod == Method.GET && request.getBody() == null) {
                String token = getTokenFromRequest(request);
                return this.scoreboardController.show(token);
            }
            return new Response(HttpStatus.BAD_REQUEST, "");
        }catch (MissingTokenException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }

    }
}

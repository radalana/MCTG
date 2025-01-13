package at.fhtw.swen.mctg.services.score.stats;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.services.common.BaseService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INVALID_HTTP_METHOD;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_NOT_ALLOWED;


public class UserStatsService extends BaseService {
    private final StatsController statsConstroller;
    public UserStatsService() {
        statsConstroller = new StatsController();
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        if (requestMethod == Method.GET) {
            if (request.getBody() == null) {
                String token = getTokenFromRequest(request);;
                return this.statsConstroller.getStats(token);
            }
            return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_NOT_ALLOWED);
        }
        return new Response(HttpStatus.BAD_REQUEST, INVALID_HTTP_METHOD);
    }
}

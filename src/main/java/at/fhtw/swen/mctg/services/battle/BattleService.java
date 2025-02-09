package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.exceptions.MissingTokenException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.model.Battle;
import at.fhtw.swen.mctg.services.common.AuthUtils;
import at.fhtw.swen.mctg.services.common.BaseService;
import at.fhtw.swen.mctg.services.deck.DeckController;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.REQUEST_BODY_NOT_ALLOWED;

public class BattleService extends BaseService {
    private final BattleController battleController;
    public BattleService() {
        this.battleController = new BattleController();
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        try {
            String token = getTokenFromRequest(request);
            if (requestMethod == Method.POST && request.getBody() == null) {
                return this.battleController.joinBattle(token);
            }
            return new Response(HttpStatus.BAD_REQUEST, REQUEST_BODY_NOT_ALLOWED);
        } catch (MissingTokenException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }
}

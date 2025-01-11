package at.fhtw.swen.mctg.services.common;

import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Service;

public abstract class BaseService implements Service {
    protected String getTokenFromRequest(Request request) {
        return AuthUtils.extractToken(request.getHeaderMap().getHeader("Authorization"));
    }
}

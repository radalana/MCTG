package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.common.BaseService;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

public class TradingService extends BaseService {
    private final TradingController tradingController;
    private final AuthenticationService authenticationService;
    public TradingService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        tradingController = new TradingController();
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        try {
            String token = getTokenFromRequest(request);
            String body = request.getBody();
            if (requestMethod == Method.POST && body != null) {
                if (request.getPathParts().size() > 1) {
                    String dealId = request.getPathParts().get(1);
                    return this.tradingController.completeDeal(token, dealId, body);
                }
                return this.tradingController.createTradingDeal(token, body);
            }
            if (requestMethod == Method.GET && body == null) {
                return this.tradingController.listDeals(token);
            }
            if (requestMethod == Method.DELETE && body == null) {
                String dealId = request.getPathParts().get(1);
                return this.tradingController.deleteOffer(token, dealId);
            }

            return new Response(HttpStatus.NOT_IMPLEMENTED, "trading not implemented");
        }catch (IllegalArgumentException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }catch (IndexOutOfBoundsException e) {
            return new Response(HttpStatus.NOT_FOUND, "{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }
}

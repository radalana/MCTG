package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.exceptions.MissingTokenException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.MessageConstants;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.services.common.BaseService;

public class TradingService extends BaseService {
    private final TradingController tradingController;
    public TradingService() {
        tradingController = new TradingController();
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        try {
            return switch (requestMethod) {
                case GET -> handleGET(request);
                case DELETE -> handleDELETE(request);
                case POST -> handlePOST(request);
                default -> new Response(HttpStatus.BAD_REQUEST, MessageConstants.INVALID_HTTP_METHOD);
            };
        }catch (MissingTokenException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }catch (IndexOutOfBoundsException e) {
            return new Response(HttpStatus.NOT_FOUND, "{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }

    private Response handleGET(Request request) throws MissingTokenException {
        String token = getTokenFromRequest(request);
        String body = request.getBody();
        if (body == null) {
            return this.tradingController.listDeals(token);
        }
        return createBadRequestResponse();
    }
    private Response handleDELETE(Request request) throws MissingTokenException{
        String token = getTokenFromRequest(request);
        String body = request.getBody();
        if (request.getPathParts().size() < 2) {
            return createBadRequestResponse();
        }
        if (body == null) {
            String dealId = request.getPathParts().get(1);
            return this.tradingController.deleteOffer(token, dealId);
        }
        return createBadRequestResponse();
    }
    private Response handlePOST(Request request) throws MissingTokenException{
        String token = getTokenFromRequest(request);
        String body = request.getBody();
        if (request.getPathParts().size() > 1) {
            String dealId = request.getPathParts().get(1);
            return this.tradingController.completeDeal(token, dealId, body);
        }
        return this.tradingController.createTradingDeal(token, body);
    }

    private Response createBadRequestResponse() {
        return new Response(HttpStatus.BAD_REQUEST, "");
    }
}

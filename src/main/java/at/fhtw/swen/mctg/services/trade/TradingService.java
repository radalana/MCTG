package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.login.AuthenticationService;

public class TradingService implements Service {
    private final TradingController tradingController;
    private final AuthenticationService authenticationService;
    public TradingService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        tradingController = new TradingController();
    }

    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        String urlParams = request.getParams();
        //System.out.println("Url params: " + urlParams);

        String pathName = request.getPathname();
        //System.out.println("Path name: " + pathName);
        try {
            String rawToken = request.getHeaderMap().getHeader("Authorization");
            String token = authenticationService.extractToken(rawToken);
            String body = request.getBody();


            if (requestMethod == Method.POST && body != null) {
                return this.tradingController.createTradingDeal(token, body);
            }
            if (requestMethod == Method.GET && body == null) {
                return this.tradingController.listDeals(token);
            }
            //TODO delete
            return new Response(HttpStatus.NOT_IMPLEMENTED, "trading not implemented");
        }catch (IllegalArgumentException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }
}

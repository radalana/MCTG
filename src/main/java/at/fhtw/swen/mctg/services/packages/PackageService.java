package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.exceptions.MissingTokenException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.common.BaseService;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.NO_CARDS_PROVIDED;

public class PackageService extends BaseService {
    private final PackageController packageController;

    public PackageService() {
        this.packageController = new PackageController(); //не знаю или создать сразу в поле
    }
    @Override
    public Response handleRequest(Request request) {
        try {
            Method requestMethod = request.getMethod();
            String token = getTokenFromRequest(request);

            if (!token.equals("admin-mtcgToken")) {
                return new Response(HttpStatus.FORBIDDEN, "{ \"message\", \"You don't have permission to access this resource.\"}");
            }
            if (requestMethod == Method.POST && request.getBody() != null) {
                return this.packageController.createPackage(request);
            }
            return new Response(HttpStatus.BAD_REQUEST, NO_CARDS_PROVIDED);
        }catch (MissingTokenException e) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"" + e.getMessage() + "\" }");
        }

    }
}

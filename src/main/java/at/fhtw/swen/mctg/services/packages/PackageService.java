package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.httpserver.server.Service;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.NO_CARDS_PROVIDED;

public class PackageService implements Service {
    private final PackageController packageController;

    public PackageService() {
        this.packageController = new PackageController(); //не знаю или создать сразу в поле
    }
    @Override
    public Response handleRequest(Request request) {
        Method requestMethod = request.getMethod();
        //или здесь добавить чтобы проверил токены и все остальное
        //1. вытащить токен из запроса
        String token = request.getHeaderMap().getHeader("Authorization");
        System.out.println(token);

        //2.если токен существует
        //когда решится многопоточность, и чтобы вызывать метод из объекта isTokenValid
        if (token == null || token.isEmpty()) {
            return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\", \"Access token is missing or invalid\"}");
        }
        //TODO проверить токен с токеном из базы данных
        //3. если токен админа можно, соотвутсвующий метод можно назвать isAdmin
        if (!token.equals("Bearer admin-mtcgToken")) {
            return new Response(HttpStatus.FORBIDDEN, "{ \"message\", \"You don't have permission to access this resource.\"}");
        }
        if (requestMethod == Method.POST && request.getBody() != null) {
                return this.packageController.createPackage(request);
        }
        return new Response(HttpStatus.BAD_REQUEST, NO_CARDS_PROVIDED);

        //request.getHeaderMap().print();
    }
}

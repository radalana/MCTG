package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

public class PackageMaker {
    //TODO избежать дублирования карт в системе, а не внутри самого пакета, код 409 см. mtcg-api
    //не знаю нужен ли тут AutificationService, и если да, то объект создать здесь или в компьютере
    public Response createPackage(Request request) {
        //проверить заголовки
            //1. что токен есть - иначе 401 незарегистрирован
            //2. что токен - это токен админа - иначе 403 - не админ
        /*
            ArrayList<Card> = вытащить из запроса
            Package package = new Package(ArrayList<Card>)
            // store.save(package)
            return ok.created
         */
        return new Response(
                HttpStatus.BAD_REQUEST,
                "{ \"message\", \"test headers\"}"
        );
    }
}

package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.core.cards.factories.CardFactory;
import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PackageMaker extends Controller {
    //TODO избежать дублирования карт в системе, а не внутри самого пакета, код 409 см. mtcg-api
    //не знаю нужен ли тут AutificationService, и если да, то объект создать здесь или в компьютере
    public Response createPackage(Request request) {
        try {
            //todo: проблема Card не может быть десириализированым так как он аобстрактный и не проходит untegration test
            //вариант 1: сначала создает не Card а либо рандомные обхект либо map и потом исходя из имени создается соответсвующая карта
            //вариант 2: создаем еще один класс для карты который не абстрактный
            List<CardData> cards= this.getObjectMapper().readValue(request.getBody(), new TypeReference<List<CardData>>(){});
            CardFactory cardFactory = new CardFactory();
            for (var cardData : cards) {
                System.out.println(cardData);
                Card card = cardFactory.createCard(cardData);
            }
         /*
            Package package = new Package(ArrayList<Card>)
            // store.save(package)
            return ok.created
         */
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    "{ \"message\", \"test headers\"}"
            );
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }

    }
}

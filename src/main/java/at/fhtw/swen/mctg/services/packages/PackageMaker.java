package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.core.cards.factories.CardFactory;
import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Package;
import at.fhtw.swen.mctg.model.dto.CardData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageMaker extends Controller {
    //TODO избежать дублирования карт в системе, а не внутри самого пакета, код 409 см. mtcg-api
    //не знаю нужен ли тут AutificationService, и если да, то объект создать здесь или в компьютере
    public Response createPackage(Request request) {
        try {
            List<CardData> cardsData= this.getObjectMapper().readValue(request.getBody(), new TypeReference<List<CardData>>(){});
            CardFactory cardFactory = new CardFactory();

            List<Card> cards = cardsData.stream()
                    .map(cardFactory::createCard)
                    .toList();

                Package cardPackage = new Package(cards);
                System.out.println(cardPackage);
                /*
                packageDao.save(cardPackage);

                 */
                return new Response(
                        HttpStatus.CREATED,
                        "{ \"message\": \"Package created successfully\"}"
                );

        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }catch (IllegalArgumentException e) {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        "{ \"message\": \"" + e.getMessage() + "\" }"
                );
        }
//        catch (SQLException e) {
//                return new Response(
//                        HttpStatus.INTERNAL_SERVER_ERROR,
//                        "{ \"message\": \"" + e.getMessage() + "\" }"
//                );
//            }

    }
}

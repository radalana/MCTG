package at.fhtw.swen.mctg.services.packages;

import at.fhtw.swen.mctg.core.cards.factories.CardFactory;
import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Package;
import at.fhtw.swen.mctg.model.dto.CardData;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.PackageDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INVALID_NUMBER_OF_CARDS;
import static at.fhtw.swen.mctg.model.Package.EXACT_CARDS_REQUIRED;

public class PackageController extends Controller {
    //TODO избежать дублирования карт в системе, а не внутри самого пакета, код 409 см. mtcg-api
    //не знаю нужен ли тут AutificationService, и если да, то объект создать здесь или в компьютере
    public Response createPackage(Request request) {
        UnitOfWork unitOfWork = new UnitOfWork();
        try (unitOfWork){
            List<CardData> cardsData= this.getObjectMapper().readValue(request.getBody(), new TypeReference<List<CardData>>(){});
            if (cardsData.size() != EXACT_CARDS_REQUIRED){
                return new Response(HttpStatus.BAD_REQUEST, INVALID_NUMBER_OF_CARDS);
            }
            CardFactory cardFactory = new CardFactory();
            List<Card> cards = cardsData.stream()
                    .map(cardFactory::createCardFromName)
                    .toList();
            Package cardPackage = new Package();
            int packageId = new PackageDao(unitOfWork).save(cardPackage);
            for(Card card : cards) {
                new CardDao(unitOfWork).save(card, packageId);
            }
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.CREATED,
                    "{ \"message\": \"Package created successfully\"}"
            );
        }catch (JsonProcessingException e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }catch (IllegalArgumentException e) {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        "{ \"message\": \"" + e.getMessage() + "\" }"
                );
        }catch (DataAccessException e){
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    "{ \"message\" : \"" + e.getMessage() + "\" }"
            );
        }catch(Exception e) {
            System.err.println(e.getMessage() + e.getClass().getName());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\" : \""  + e.getMessage() + "\"}"
            );
        }

    }
}

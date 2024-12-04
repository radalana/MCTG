package at.fhtw.swen.mctg.services.cards;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;


public class StackController extends Controller {
    public Response listAllCards(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            UserRepository userRepository = new UserRepository(unitOfWork);
            User user = userRepository.findUserByToken(token);
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
            }
            int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            CardService cardService = new CardService(new CardDao(unitOfWork));
            List<Map<String, Object>> cardsAsMap = cardService.getAllCardsAsMap(stackId);
            String json = new ObjectMapper().writeValueAsString(cardsAsMap);
            return new Response(HttpStatus.OK, json);
        }catch (Exception e) {
            System.err.println("Error occurred in /GET/cards:");
            System.err.println("Token: " + token); // Входной параметр
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }

    }
}

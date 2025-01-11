package at.fhtw.swen.mctg.services.cards;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.cards.CardDao;
import at.fhtw.swen.mctg.persistence.dao.user.UserRepository;
import at.fhtw.swen.mctg.services.common.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;


public class StackController extends Controller {
    public Response listAllCards(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            UserRepository userRepository = new UserRepository(unitOfWork);
            User user = UserManager.validateAndFetchUser(token, unitOfWork);
            CardService cardService = new CardService(new CardDao(unitOfWork));
            List<Map<String, Object>> cardsAsMap = cardService.getAllCardsAsMap(user.getId());
            String json = new ObjectMapper().writeValueAsString(cardsAsMap);
            return new Response(HttpStatus.OK, json);
        } catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
        }catch (Exception e) {
            System.err.println("Error occurred in /GET/cards:");
            System.err.println("Token: " + token); // Входной параметр
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }

    }
}

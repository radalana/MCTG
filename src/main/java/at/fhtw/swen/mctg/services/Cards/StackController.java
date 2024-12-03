package at.fhtw.swen.mctg.services.Cards;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;

import java.util.ArrayList;

public class StackController extends Controller {
    public Response listAllCards(String token) {
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            UserRepository userRepository = new UserRepository(unitOfWork);
            User user = userRepository.findUserByToken(token);
            //TODO wenn user null - change implementaion in finduserByToken
            if (user == null) {
                return new Response(HttpStatus.UNAUTHORIZED, "{ \"message\": \"User not found\" }\"}");
            }
            /*
            int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            ArrayList<Card> cards = new CardDao(unitOfWork).getCardsByStackId(stackId);
            user.getStack().addCards(cards);
            if (cards.isEmpty()) {
                return new Response(HttpStatus.OK, "{ \"message\": \"Your stack is empty, buy cards\" }\"}");
            }
             */
            return new Response(HttpStatus.OK, "{cards}");
        }catch (DataAccessException e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}

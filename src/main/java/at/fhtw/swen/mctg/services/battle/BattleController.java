package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Battle;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.BattleRepository;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;

import java.util.List;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;

public class BattleController {
   public Response joinBattle(String token) {
       //check if deck configured
       try(UnitOfWork unitOfWork = new UnitOfWork()) {
           User user = new UserRepository(unitOfWork).findUserByToken(token);
           if (user == null) {
               return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND)
           }
           //getDeck
           List<Card> cards = getUserDeck(user, unitOfWork);
           if (cards.isEmpty()) {
               return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }");
           }
           Deck deck = user.getDeck();
           deck.addCard((Card) cards);

           //запрос на battle
           User opponent; // = findOpponent();
           //если есть кто ждет battle
                runBattle(user, opponent);
            new BattleRepository(unitOfWork).save(battle);
            //вернуть отвтет выиграл или нет
           //иначе (т.е opponent == null) сохраняить запрос
           //вернуть ответ с accepted
           return new Response(HttpStatus.ACCEPTED, "stated wil be updated bla bla ckeck later");

       }catch (Exception e) {
           System.err.println(e.getMessage());
           return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
       }
   }

   private List<Card> getUserDeck(User user, UnitOfWork unitOfWork) {
       int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
       return new CardDao(unitOfWork).getCardsInDeckByStackId(stackId);
   }

   private void runBattle(User user1, User user2) {}
}

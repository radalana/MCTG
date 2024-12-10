package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.Battle;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.*;

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
           BattleRequestsRepository battleRequestsRepo = new BattleRequestsRepository(unitOfWork);
           User opponent  = findOpponent(battleRequestsRepo); //search if there are any requests for a battle
           if (opponent == null) {
               battleRequestsRepo.save(user.getId());
               return new Response(HttpStatus.ACCEPTED, "{ \"message\": \"Battle request submitted. No opponents are available at the moment. The battle will be processed once an opponent is found.\" }");
           } else {
               //if there is opponent for battle
               Battle battle = startBattle(user, opponent);
               //new BattleRepository(unitOfWork).save(battle);
               return new Response(HttpStatus.NOT_IMPLEMENTED, "battle not implemented");
           }
       }catch (Exception e) {
           System.err.println(e.getMessage());
           return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
       }
   }

   private List<Card> getUserDeck(User user, UnitOfWork unitOfWork) {
       int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
       return new CardDao(unitOfWork).getCardsInDeckByStackId(stackId);
   }

   private User findOpponent(BattleRequestsRepository battleRequestsRepository) throws DataAccessException {
       return battleRequestsRepository.findUserOfEarliestRequest();
   }
   private at.fhtw.swen.mctg.model.Battle startBattle(User user1, User user2) {
       //get deck1 und get deck2
       Deck deck1 = user1.getDeck(); //пока пустой нужно из дб получить deck
       Deck deck2 = user2.getDeck();
       battle(deck1, deck2);
   }
}

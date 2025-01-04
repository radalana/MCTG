package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.*;
import at.fhtw.swen.mctg.persistence.BattleResultRepository;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.*;
import at.fhtw.swen.mctg.core.engine.BattleEngine;

import java.util.List;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;

public class BattleController {
   public Response joinBattle(String token) {
       //uberprüfe ob Deck konfiguriert ist
       try(UnitOfWork unitOfWork = new UnitOfWork()) {
           User user = new UserRepository(unitOfWork).findUserByToken(token);
           if (user == null) {
               return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
           }
           //getDeck
           //int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
           List<Card> cards = getUserDeck(user, unitOfWork);
           if (cards.isEmpty()) {
               return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
           }

           boolean a = user.getDeck().addCards(cards);
           System.err.println("add cards to deck: " + a);
           Deck deck = user.getDeck();
           System.out.println("deck: " + deck.getCards());

           BattleRequestsRepository battleRequestsRepo = new BattleRequestsRepository(unitOfWork);
           synchronized (this) {
               //TODO for opponent deck herunterladen
               User opponent = findOpponent(battleRequestsRepo); //search if there are any requests for a battle
               if (opponent == null) {
                   System.err.println(user.getLogin() + " creates request for a battle");
                   battleRequestsRepo.save(user.getId());
                   unitOfWork.commitTransaction();
                   return new Response(HttpStatus.ACCEPTED, "{ \"message\": \"Battle request submitted. No opponents are available at the moment. The battle will be processed once an opponent is found.\" }\n");

               } else {
                   System.err.println(user.getLogin() + " plays with " + opponent.getLogin());
                   //Delete record of opponent's request from battle_requests
                   battleRequestsRepo.delete(opponent.getId());
                   //int stackOppId = new StackRepository(unitOfWork).findStackByUsername(opponent.getLogin());
                   List<Card> opponentCards = getUserDeck(opponent, unitOfWork);
                   if (opponentCards.isEmpty()) {
                       return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
                   }
                   opponent.getDeck().addCards(opponentCards);
                   System.out.println("-----joinBattle---------");
                   System.err.println(user.getLogin() + " " + user.getDeck());
                   System.err.println(opponent.getLogin() + " " + opponent.getDeck());
                   //start battle
                   BattleEngine engine = new BattleEngine(user, opponent);
                   Battle battle = engine.startBattle();

                   //save battle in db
                   int userResultId = new BattleResultRepository(unitOfWork).save(battle.getUser1BattleResult());
                   int opponentResultId = new BattleResultRepository(unitOfWork).save(battle.getUser2BattleResult());
                   new BattleRepository(unitOfWork).save(battle, userResultId, opponentResultId);


                   //Update user's and opponent's stats accordingly in db
                   StatsRepository stateRepo = new StatsRepository(unitOfWork);

                   Stats userStats = stateRepo.findStats(user.getId());
                   Stats opponentStats = stateRepo.findStats(opponent.getId());

                   updateStates(userStats, opponentStats, battle);

                   stateRepo.save(userStats);
                   stateRepo.save(opponentStats);

                   /*System.err.println("Battle statistics: " + battle);
                   List<Round> rounds = battle.getRounds();
                   for (Round round : rounds) {
                       System.out.println(round);
                   }*/

                   unitOfWork.commitTransaction();
                   String result = String.format("----- Battle summary-----\n\n"
                           + "\tRounds played: %d\n"
                           + "\t%s won %d rounds\n"
                           + "\t%s won %d rounds\n"
                           + "\tThe battle had %d draw rounds\n",
                   battle.getNumberOfRounds(), user.getLogin(), battle.getUser1BattleResult().getResult(),
                   opponent.getLogin(), battle.getUser2BattleResult().getResult(), battle.getDrawRounds());


                   return new Response(HttpStatus.OK, battle.getRounds().toString() + result);
               }
           }
       }catch (IllegalArgumentException e) {
           return new Response(HttpStatus.BAD_REQUEST, e.getMessage());
       }catch (Exception e) {
           System.err.println(e.getMessage());
           e.printStackTrace();
           return new Response(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
       }
   }
//TODO должна может быть вынести отдельно в логику states
   private void updateStates(Stats userStats, Stats opponentStats, Battle battle) {
       int userScore = battle.getUser1BattleResult().getResult();
       int opponentScore = battle.getUser2BattleResult().getResult();

       if (userScore > opponentScore) {
           userStats.recordWin();
           opponentStats.recordLoss();
       }else if (opponentScore > userScore) {
           userStats.recordLoss();
           opponentStats.recordWin();
       }else {
           userStats.recordDraw();
           opponentStats.recordDraw();
       }
   }

   private List<Card> getUserDeck(User user, UnitOfWork unitOfWork) {

       List<Card> cards = new CardDao(unitOfWork).getCardsInDeckByUserId(user.getId());
       cards.forEach(card -> card.setOwnerName(user.getLogin()));
       return cards;
   }

   private User findOpponent(BattleRequestsRepository battleRequestsRepository) throws DataAccessException {
       return battleRequestsRepository.findUserOfEarliestRequest();
   }


}

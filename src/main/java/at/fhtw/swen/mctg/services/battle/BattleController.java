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
           List<Card> cards = getUserDeck(user, unitOfWork);
           if (cards.isEmpty()) {
               return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
           }

           user.getStack().addCards(cards);

           BattleRequestsRepository battleRequestsRepo = new BattleRequestsRepository(unitOfWork);
           synchronized (this) {
               //TODO for opponent deck herunterladen
               User opponent = findOpponent(battleRequestsRepo); //search if there are any requests for a battle
               if (opponent == null) {
                   battleRequestsRepo.save(user.getId());
                   unitOfWork.commitTransaction();
                   return new Response(HttpStatus.ACCEPTED, "{ \"message\": \"Battle request submitted. No opponents are available at the moment. The battle will be processed once an opponent is found.\" }\n");

               } else {
                   //if there is opponent for battle
                   List<Card> opponentCards = getUserDeck(opponent, unitOfWork);
                   if (opponentCards.isEmpty()) {
                       return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
                   }
                   opponent.getStack().addCards(cards);
                   BattleEngine engine = new BattleEngine(user, opponent);
                   Battle battle = engine.startBattle();
                   int userResultId = new BattleResultRepository(unitOfWork).save(battle.getUser1BattleResult());
                   int opponentResultId = new BattleResultRepository(unitOfWork).save(battle.getUser2BattleResult());
                   new BattleRepository(unitOfWork).save(battle, userResultId, opponentResultId);

                   StatsRepository stateRepo = new StatsRepository(unitOfWork);

                   Stats userStats = stateRepo.findStats(user.getId());
                   Stats opponentStats = stateRepo.findStats(opponent.getId());

                   updateStates(userStats, opponentStats, battle);
                   stateRepo.save(userStats);
                   stateRepo.save(opponentStats);

                   unitOfWork.commitTransaction();
                   return new Response(HttpStatus.OK, "{ \"message\": \"Check your stat to see result of a battle with " + opponent.getLogin() + ".\"}\n");
               }
           }
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
       int stackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
       return new CardDao(unitOfWork).getCardsInDeckByStackId(stackId);
   }

   private User findOpponent(BattleRequestsRepository battleRequestsRepository) throws DataAccessException {
       return battleRequestsRepository.findUserOfEarliestRequest();
   }


}

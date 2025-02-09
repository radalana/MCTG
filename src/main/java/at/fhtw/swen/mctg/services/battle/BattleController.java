package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.*;
import at.fhtw.swen.mctg.persistence.dao.battle.BattleResultRepository;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.core.engine.BattleEngine;
import at.fhtw.swen.mctg.persistence.dao.battle.BattleRepository;
import at.fhtw.swen.mctg.persistence.dao.battle.BattleRequestsRepository;
import at.fhtw.swen.mctg.persistence.dao.cards.CardDao;
import at.fhtw.swen.mctg.persistence.dao.user.StatsRepository;
import at.fhtw.swen.mctg.services.common.UserManager;

import java.util.List;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.INTERNAL_SERVER_ERROR;
import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.USER_NOT_FOUND;

public class BattleController {
   public Response joinBattle(String token) {
       //uberprüfe ob Deck konfiguriert ist
       try(UnitOfWork unitOfWork = new UnitOfWork()) {
           User user = UserManager.validateAndFetchUser(token, unitOfWork);
           List<Card> cards = getUserDeck(user, unitOfWork);
           if (cards.isEmpty()) {
               return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
           }

           boolean a = user.getDeck().addCards(cards);
           Deck deck = user.getDeck();

           BattleRequestsRepository battleRequestsRepo = new BattleRequestsRepository(unitOfWork);
           synchronized (this) {
               User opponent = findOpponent(battleRequestsRepo); //search if there are any requests for a battle
               if (opponent == null) {
                   System.err.println(user.getUsername() + " creates request for a battle");
                   battleRequestsRepo.save(user.getId());
                   unitOfWork.commitTransaction();
                   return new Response(HttpStatus.ACCEPTED, "{ \"message\": \"Battle request submitted. No opponents are available at the moment. The battle will be processed once an opponent is found.\" }\n");

               } else {
                   System.err.println(user.getUsername() + " plays with " + opponent.getUsername());
                   //Delete record of opponent's request from battle_requests
                   battleRequestsRepo.delete(opponent.getId());
                   //int stackOppId = new StackRepository(unitOfWork).findStackByUsername(opponent.getLogin());
                   List<Card> opponentCards = getUserDeck(opponent, unitOfWork);
                   if (opponentCards.isEmpty()) {
                       return new Response(HttpStatus.BAD_REQUEST, "{ \"message\": \"Deck is empty. To start battle configure a stack.\" }\n");
                   }
                   opponent.getDeck().addCards(opponentCards);
                   //System.out.println("-----joinBattle---------");
                   //System.err.println(user.getUsername() + " " + user.getDeck());
                   //System.err.println(opponent.getUsername() + " " + opponent.getDeck());
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

                   //reset players deck and add distribute cared to new owner
                   CardDao cardDao = new CardDao(unitOfWork);
                   cardDao.updateDeckOwnership(user);
                   cardDao.unsetDeckFlag(user);

                   cardDao.updateDeckOwnership(opponent);
                   cardDao.unsetDeckFlag(opponent);

                   //System.out.println("stack after battle:");
                   System.out.println(user.getStack());
                   //System.out.println(user.getUsername() + "'s deck after battle:");
                   //System.out.println(user.getDeck());
                   //opponent.getStack().returnDeckToStack();
                   //System.out.println(opponent.getUsername() + "'s deck after battle:");
                   //System.out.println(opponent.getDeck());


                   unitOfWork.commitTransaction();
                   String result = String.format("\n------ Battle summary ------\n\n"
                           + "\tRounds played: %d\n"
                           + "\t%s won %d rounds\n"
                           + "\t%s won %d rounds\n"
                           + "\tThe battle had %d draw rounds\n",
                   battle.getNumberOfRounds(), user.getUsername(), battle.getUser1BattleResult().getVictories(),
                   opponent.getUsername(), battle.getUser2BattleResult().getVictories(), battle.getDrawRounds());
                   return new Response(HttpStatus.OK, engine.getLog().toString() + result);
               }
           }
       }catch (UserNotFoundException e) {
            return new Response(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND);
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
       int userScore = battle.getUser1BattleResult().getVictories();
       int opponentScore = battle.getUser2BattleResult().getVictories();

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
       cards.forEach(card -> card.setOwnerName(user.getUsername()));
       return cards;
   }

   private User findOpponent(BattleRequestsRepository battleRequestsRepository) throws DataAccessException {
       return battleRequestsRepository.findUserOfEarliestRequest();
   }


}

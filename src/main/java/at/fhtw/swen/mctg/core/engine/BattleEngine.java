package at.fhtw.swen.mctg.core.engine;

import at.fhtw.swen.mctg.model.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BattleEngine {
    private static final int MAX_ROUNDS = 100;
    //TODO dont know log better field of engine or what
    private final List<String> log = new ArrayList<>();
    User user1;
    User user2;
    private final RoundEngine roundEngine;
    public BattleEngine(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.roundEngine = new RoundEngine(user1, user2);
    }
    public Battle startBattle() throws IllegalArgumentException{
       //get deck1 und get deck2
       // System.out.println("----Starting Battle-------");
        //System.out.println(user1.getUsername() + " " + user1.getDeck());
        //System.out.println(user2.getUsername() + " " + user2.getDeck());
       Deck deck1 = user1.getDeck(); //пока пустой нужно из дб получить deck
       Deck deck2 = user2.getDeck();
        //System.err.println("Deck1: " + deck1.toString());
       List<Round> rounds = runRounds(deck1, deck2);
       int user1Results = calculateResult(rounds, user1);
       int user2Result = calculateResult(rounds, user2);
       return new Battle(rounds, new BattleResult(user1.getId(), user1Results), new BattleResult(user2.getId(), user2Result));
   }

    private List<Round> runRounds(Deck deck1, Deck deck2) throws IllegalArgumentException{
        List<Round> rounds = new ArrayList<>();
        //System.err.println("engine.runRounds: " + " deck1: " + deck1 + " deck2: " + deck2);
        for(int i = 0; i < MAX_ROUNDS; i++) {
            //System.out.println("round number: " + i);
            if (deck1.isEmpty() || deck2.isEmpty()) {
                //System.err.println("one or more decks are empty");
                break;
            }
          //  System.out.println("after check empty decks round number: " + i);
            Round round = roundEngine.run();
            round.setRoundNumber(i+1);
            if (!round.isDraw()) {
                log.add(round.toString());
                Card winnerCard = round.getWinnerCard();
                String winner = winnerCard.getOwnerName();
                Card lostCard = round.getLooserCard();
                lostCard.setOwnerName(winner);
            }
            rounds.add(round);

        }
        return rounds;
   }

    private int calculateResult(List<Round> rounds, User user) {
        return (int) rounds.stream()
                .filter(round -> !round.isDraw() && round.getWinner().equals(user))
                .count();
    }
}

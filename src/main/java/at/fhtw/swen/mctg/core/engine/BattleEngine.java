package at.fhtw.swen.mctg.core.engine;

import at.fhtw.swen.mctg.model.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class BattleEngine {
    private static final int MAX_ROUNDS = 100;
    User user1;
    User user2;
    private final RoundEngine roundEngine;
    public BattleEngine(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.roundEngine = new RoundEngine(user1, user2);
    }
    public Battle startBattle() {
       //get deck1 und get deck2
       Deck deck1 = user1.getDeck(); //пока пустой нужно из дб получить deck
       Deck deck2 = user2.getDeck();
       //battle(deck1, deck2);
       List<Round> rounds = runRounds(deck1, deck2);
       int user1Results = calculateResult(rounds, user1);
       int user2Result = calculateResult(rounds, user2);
       return new Battle(rounds, new BattleResult(user1.getId(), user1Results), new BattleResult(user2.getId(), user2Result));
   }

    private List<Round> runRounds(Deck deck1, Deck deck2) {
        List<Round> rounds = new ArrayList<>();
        // round важно только победитель и карта
        for(int i = 0; i < MAX_ROUNDS; i++) {
            if (deck1.isEmpty() || deck2.isEmpty()) {
                break;
            }
            Round round = roundEngine.run();
            round.setRoundNumber(i+1);
            rounds.add(round);

        }
        return rounds;
   }

    private int calculateResult(List<Round> rounds, User user) {
        //посчитать сколько раз user == winner
        return (int) rounds.stream()
                .filter(round -> user.equals(round.getWinner()))
                .count();
    }
}

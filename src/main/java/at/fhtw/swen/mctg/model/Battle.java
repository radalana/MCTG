package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Battle {
    private final List<Round> rounds;
    private final int numberOfRounds;
    private final BattleResult user1BattleResult;
    private final BattleResult user2BattleResult;

    public Battle(List<Round> rounds,BattleResult user1BattleResult,BattleResult user2BattleResult) {
        this.rounds = rounds;
        this.numberOfRounds = rounds.size();
        this.user1BattleResult = user1BattleResult;
        this.user2BattleResult = user2BattleResult;
    }

    @Override
    public String toString() {
        return "Number in rounds: " + numberOfRounds + " ,\n" + user1BattleResult.toString() + " vs\n" + user2BattleResult.toString();
    }
}

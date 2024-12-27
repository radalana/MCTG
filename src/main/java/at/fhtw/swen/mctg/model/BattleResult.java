package at.fhtw.swen.mctg.model;

import lombok.Getter;

@Getter
public class BattleResult {
    private final int userId;
    private final int result;

    public BattleResult(int userId, int result) {
        this.userId = userId;
        this.result = result;
    }
}

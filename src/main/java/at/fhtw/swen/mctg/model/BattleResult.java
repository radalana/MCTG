package at.fhtw.swen.mctg.model;

import lombok.Getter;

@Getter
public class BattleResult {
    private final int userId;
    private final int victories;

    public BattleResult(int userId, int victories) {
        this.userId = userId;
        this.victories = victories;
    }

    @Override
    public String toString() {
        return "user: " + userId + ", victories: " + victories;
    }
}

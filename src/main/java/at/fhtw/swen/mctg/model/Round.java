package at.fhtw.swen.mctg.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Round {
    private final Card defeatedCard;
    private int roundNumber;
    @Getter
    private User winner;
    @Getter
    private User looser;

    public Round(User winner, User looser, Card defeatedCard) {
        this.defeatedCard = defeatedCard;
        this.winner = winner;
        this.looser = looser;
    }

    @Override
    public String toString() {
        return "Round number: " + roundNumber + ", winner: " + winner + ", looser: " + looser;
    }

}



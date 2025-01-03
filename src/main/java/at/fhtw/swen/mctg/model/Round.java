package at.fhtw.swen.mctg.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Round {
    private Card winnerCard;
    private Card looserCard;
    private int roundNumber;
    private double effectiveness;
    private boolean isDraw;

    public Round(Card card1, Card card2, double effectiveness, boolean isDraw) {
        this.winnerCard = card1;
        this.looserCard = card2;
        this.isDraw = isDraw;
        this.effectiveness = effectiveness;
    }

    public Round(Card card1, Card card2, double effectiveness) {
        this(card1, card2, effectiveness, false);
    }
    //for HTTP answer
    @Override
    public String toString() {
        if (isDraw) {
            return String.format("Round %d:\n" +
                            "  Result: Draw\n" +
                            "  Card 1: %s (Player: %s, Damage:  %.1f)\n" +
                            "  Card 2: %s (Player: %s, Damage: %.1f\n" +
                            "  Element Matchup: %s vs %s", roundNumber, winnerCard.getName(), winnerCard.getOwnerName(),
                    winnerCard.getDamage(), looserCard.getName(), looserCard.getOwnerName(), looserCard.getDamage(),
                    winnerCard.getElement().getValue(), looserCard.getElement().getValue());
        } else {
            return String.format("Round %d:\n" +
                            "  Winning Card: %s (Owner: %s, Damage: %.1f)\n" +
                            "  Defeated Card: %s (Owner: %s, Damage: %.1f)\n" +
                            "  Element Effectiveness: %.1f (%s vs %s)\n",
                    roundNumber, winnerCard.getName(), winnerCard.getOwnerName(), winnerCard.getDamage(),
                    looserCard.getName(), looserCard.getOwnerName(), looserCard.getDamage(),
                    effectiveness, winnerCard.getElement().getValue(), looserCard.getElement().getValue());
        }

    }

}



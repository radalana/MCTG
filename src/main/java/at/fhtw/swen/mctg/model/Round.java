package at.fhtw.swen.mctg.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Round {
    private User winner;
    private Card winnerCard;
    private Card looserCard;
    private int roundNumber;
    private double effectiveness;
    private boolean isDraw;

    public Round(Card card1, Card card2, double effectiveness, User winner, boolean isDraw) {
        this.winnerCard = card1;
        this.looserCard = card2;
        this.isDraw = isDraw;
        this.effectiveness = effectiveness;
        this.winner = winner;
    }

    public Round(Card card1, Card card2, double effectiveness, User winner) {
        this(card1, card2, effectiveness, winner,false);
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
                    winnerCard.getElementAsString(), looserCard.getElementAsString());
        } else {
            return String.format("Round %d:\n" +
                            "  Winning Card: %s (Owner: %s, Damage: %.1f, Element: %s)\n" +
                            "  Defeated Card: %s (Owner: %s, Damage: %.1f, Element: %s)\n" +
                            "  Element Effectiveness: %.1f (%.2f vs %.2f)\n",
                    roundNumber, winnerCard.getName(), winnerCard.getOwnerName(), winnerCard.getDamage(), winnerCard.getElementAsString(),
                    looserCard.getName(), looserCard.getOwnerName(), looserCard.getDamage(), looserCard.getElementAsString(),
                    effectiveness, winnerCard.getDamage() * effectiveness, looserCard.getDamage());
        }

    }

}



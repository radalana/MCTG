package at.fhtw.swen.mctg.core.engine;

import at.fhtw.swen.mctg.core.ElementFactors;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Round;
import at.fhtw.swen.mctg.model.User;


public class RoundEngine {
    private final User user1;
    private final User user2;
    public RoundEngine(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
    public Round run() throws IllegalArgumentException{
        System.out.println("---round.run----");
        System.err.println(user1.getDeck());
        System.err.println(user2.getDeck());
        Card card1 = user1.getDeck().pickRandomCard();
        Card card2 = user2.getDeck().pickRandomCard();
        System.out.println("Card 1: " + card1);
        System.out.println("Card 2: " + card2);
        double effectiveness = ElementFactors.getMultiplier(card1.getElement(), card2.getElement());
        int result =  startFight(card1, card2, effectiveness);

        if (result > 0) {
            return new Round(card1, card2, effectiveness, false);
        }if (result < 0) {
            return new Round(card2, card1, effectiveness, false);
        } else {
            return new Round(card1, card2, effectiveness, true);
        }
        /*
        System.out.println("result in run method: " + result);
        User winner = definePlayer(result, true);
        User looser = definePlayer(result, false);
        System.err.println("user1 " + user1);
        System.err.println("winner " + winner);
        Card defeatedCard = defineDefeatedCard(result, card1, card2);
        transferDefeatedCard(winner, looser, defeatedCard);

        return new Round(winnerCard, looserCard, isDraw);

         */
    }

    private void transferDefeatedCard(User winner, User looser, Card card) {
        if (winner == null || looser == null || card == null) {
            return;
        }
        winner.getDeck().addCard(card);
        looser.getDeck().removeCard(card);
    }
    private Card defineDefeatedCard(int result, Card card1, Card card2) {

        return switch (result) {
            case 1 -> card2;
            case -1 -> card1;
            case 0 -> null;
            default -> throw new IllegalStateException("Unexpected value: " + result);
        };
    }

    private User definePlayer(int result, boolean isWinner) {
        return switch (result) {
            case 1 -> isWinner ? user1 : user2; // user1 победил, user2 проиграл
            case -1 -> isWinner ? user2 : user1; // user2 победил, user1 проиграл
            case 0 -> null; // Ничья
            default -> throw new IllegalArgumentException("Illegal result: " + result);
        };
    }

    private int startFight(Card card1, Card card2, double effectiveness) throws IllegalArgumentException {
        //System.err.println("I am in startFight of RoundEngine");
        return card1.fight(card2, effectiveness);
    }

}

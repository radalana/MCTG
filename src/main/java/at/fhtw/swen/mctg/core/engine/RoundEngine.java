package at.fhtw.swen.mctg.core.engine;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.Round;
import at.fhtw.swen.mctg.model.User;


public class RoundEngine {
    private final User user1;
    private final User user2;
    public RoundEngine(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
    public Round run() {
        System.out.println("user 1: " + user1);
        System.out.println("user 2: " + user2);
        Card card1 = user1.getDeck().getRandomCard();
        Card card2 = user2.getDeck().getRandomCard();
        System.out.println("Card 1: " + card1);
        System.out.println("Card 2: " + card2);
        int result =  startFight(card1, card2);
        System.out.println("result in run method: " + result);
        User winner = definePlayer(result, true);
        User looser = definePlayer(result, false);
        System.err.println("user1 " + user1);
        System.err.println("winner " + winner);
        Card defeatedCard = defineDefeatedCard(result, card1, card2);
        transferDefeatedCard(winner, looser, defeatedCard);
        return new Round(winner, looser, defeatedCard);
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

    private int startFight(Card card1, Card card2) {
        if (card1.equals(card2)) {
            System.err.println("same card in startFight");
        }
        //System.err.println("I am in startFight of RoundEngine");
        System.err.println("card1: " + card1.getDamage());
        System.err.println("card2: " + card2.getDamage());
        return card1.fight(card2);
    }

}

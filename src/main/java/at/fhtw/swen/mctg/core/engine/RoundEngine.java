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
        //System.out.println("---round.run----");
        //System.err.println(user1.getDeck());
        //System.err.println(user2.getDeck());
        Card card1 = user1.getDeck().pickRandomCard();
        Card card2 = user2.getDeck().pickRandomCard();
        //System.out.println("Card 1: " + card1);
        //System.out.println("Card 2: " + card2);
        double effectiveness = ElementFactors.getMultiplier(card1.getElement(), card2.getElement());
        //если у одной из карт нет элемента то effectivness 1
        int result =  startFight(card1, card2, effectiveness);

        if (result > 0) {
            user1.getDeck().addCard(card2);
            user2.getDeck().removeCard(card2);
            //card2.setOwnerName(user1.getLogin());
            return new Round(card1, card2, effectiveness);
        }if (result < 0) {
            user2.getDeck().addCard(card1);
            user1.getDeck().removeCard(card1);
            //card1.setOwnerName(user2.getLogin());
            return new Round(card2, card1, effectiveness);
        } else {
            return new Round(card1, card2, effectiveness, true);
        }
    }


    private int startFight(Card card1, Card card2, double effectiveness) throws IllegalArgumentException {
        return card1.fight(card2, effectiveness);
    }

}

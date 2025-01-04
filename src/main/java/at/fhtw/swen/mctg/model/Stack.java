package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;
import lombok.Getter;

import java.util.List;

@Getter
public class Stack extends CardSet {
    private final Deck deck;

    public Stack() {
        super();
        deck = new Deck();
    }
    public Stack(List<Card> cards) {
        super(cards);
        deck = new Deck();
    }
    public void returnDeckToStack() {
        cards.addAll(deck.getDeck());
        deck.clear();
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stack{");
        for (Card card : cards) {
            builder.append(card.getName());
        }
        builder.append("}");
        builder.append("Deck{");
        for (Card card : deck.getDeck()) {
            builder.append(card.getName()).
                    append(": (").
                    append(card.getDamage()).
                    append(") + id( ").
                    append(card.getId()).
                    append(" )");
        }
        builder.append("}");
        return builder.toString();
    }
}

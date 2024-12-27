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

}

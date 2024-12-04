package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;

import java.util.List;

public class Deck extends CardSet {
    public static final int SIZE = 4;

    private List<Card> cards;
    public Deck() {
        super(SIZE);
    }
}

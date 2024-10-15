package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;

import java.util.ArrayList;
import java.util.List;

public class Deck extends CardSet {
    public static final int MAX_SIZE = 4;

    private List<Card> cards;
    public Deck() {
        super(MAX_SIZE);
    }



}

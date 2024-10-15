package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;

import java.util.List;

public class Package extends CardSet {
    private static final int MAX_SIZE = 5;

    public Package(List<Card> cards) {
        super(MAX_SIZE, cards);
    }
    public Package() {
        super(MAX_SIZE);
    }

}

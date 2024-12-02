package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;

import java.util.List;

public class Package extends CardSet {
    private static final int SIZE = 5;
    public static final int PACKAGE_PRICE = 5;

    public Package(List<Card> cards) {
        super(SIZE, cards);
    }
    public Package() {
        super(SIZE);
    }

}

package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;

import java.util.List;

public class Package extends CardSet {
    public static final int EXACT_CARDS_REQUIRED = 5;
    public static final int PACKAGE_PRICE = 5;

    public Package(List<Card> cards) {
        super(EXACT_CARDS_REQUIRED, cards);
    }
    public Package() {
        super(EXACT_CARDS_REQUIRED);
    }

}

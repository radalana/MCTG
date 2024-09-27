package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.Card;

public abstract class Monster extends Card {
    public Monster(int damage, Element type) {
        super(damage, type);
    }
}

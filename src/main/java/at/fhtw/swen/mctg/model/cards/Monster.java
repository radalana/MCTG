package at.fhtw.swen.mctg.model.cards;

import at.fhtw.swen.mctg.model.Card;

public abstract class Monster extends Card {
    public Monster(int damage, Element type) {
        super(damage, type);
    }
}

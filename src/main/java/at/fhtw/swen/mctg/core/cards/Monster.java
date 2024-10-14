package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;

public abstract class Monster extends Card {
    public Monster(double damage, Element type) {
        super(damage, type);
    }
    public Monster(double damage) {
        super(damage);
    }
    @Override
    public boolean isMonsterType() {
        return true;
    }
}

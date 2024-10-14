package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;

public class Spell extends Card {

    public Spell(double damage, Element type) {
        super(damage, type);
    }
    @Override
    public void attack() {
        System.out.println("Spell");
    }
    @Override
    public boolean isMonsterType() {
        return false;
    }
}

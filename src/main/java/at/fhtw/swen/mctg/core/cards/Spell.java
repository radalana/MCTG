package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.Card;

public class Spell extends Card {

    public Spell(int damage, Element type) {
        super(damage, type);
    }
    @Override
    public void attack() {
        System.out.println("Spell");
    }
}

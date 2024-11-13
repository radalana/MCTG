package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;

public class Spell extends Card {

    public Spell(String id, String name, double damage, Element element) {
        super(id, name, damage, element);
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

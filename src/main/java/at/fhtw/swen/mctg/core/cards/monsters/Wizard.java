package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Wizard extends Monster {
    public Wizard(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Casts a fireball");
    }
}

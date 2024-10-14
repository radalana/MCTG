package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Ork extends Monster {
    public Ork(double damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Swings a giant axe");
    }
}

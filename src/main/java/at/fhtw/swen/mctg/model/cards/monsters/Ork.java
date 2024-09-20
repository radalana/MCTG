package at.fhtw.swen.mctg.model.cards.monsters;

import at.fhtw.swen.mctg.model.cards.Monster;

public class Ork extends Monster {
    public Ork(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Swings a giant axe");
    }
}

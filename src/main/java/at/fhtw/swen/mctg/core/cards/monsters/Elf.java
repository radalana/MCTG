package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Elf extends Monster {
    public Elf(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Shoots an arrow");
    }
}

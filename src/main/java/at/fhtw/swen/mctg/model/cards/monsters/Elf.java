package at.fhtw.swen.mctg.model.cards.monsters;

import at.fhtw.swen.mctg.model.cards.Monster;

public class Elf extends Monster {
    public Elf(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Shoots an arrow");
    }
}

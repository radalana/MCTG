package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Elf extends Monster {
    public Elf(double damage, Element type) {
        super(damage, type);
    }
    public Elf(double damage) {super(damage);}
    @Override
    public void attack() {
        System.out.println("Shoots an arrow");
    }
}

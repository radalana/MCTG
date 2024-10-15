package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Dragon extends Monster {
    public Dragon(double damage, Element type) {
        super(damage, type);
    }
    public Dragon(double damage) {super(damage);}
    @Override
    public void attack() {
        System.out.println("Breath fire");
    }

}

package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Knight extends Monster {
    public Knight(double damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Strikes with a sword");
    }
}

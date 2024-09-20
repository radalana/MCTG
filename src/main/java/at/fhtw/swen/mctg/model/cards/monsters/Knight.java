package at.fhtw.swen.mctg.model.cards.monsters;

import at.fhtw.swen.mctg.model.cards.Monster;

public class Knight extends Monster {
    public Knight(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Strikes with a sword");
    }
}

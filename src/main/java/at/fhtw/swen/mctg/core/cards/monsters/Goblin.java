package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Goblin extends Monster {

    public Goblin(double damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Stab with a rusty dagger");
    }
}

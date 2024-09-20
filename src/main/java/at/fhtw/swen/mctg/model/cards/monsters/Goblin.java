package at.fhtw.swen.mctg.model.cards.monsters;

import at.fhtw.swen.mctg.model.cards.Monster;

public class Goblin extends Monster {

    public Goblin(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Stab with a rusty dagger");
    }
}

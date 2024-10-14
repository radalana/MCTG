package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Kraken extends Monster {
    public Kraken (double damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Smashes with tentacles");
    }


}

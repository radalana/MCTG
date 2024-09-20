package at.fhtw.swen.mctg.model.cards.monsters;

import at.fhtw.swen.mctg.model.cards.Monster;

public class Kraken extends Monster {
    public Kraken (int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Smashes with tentacles");
    }


}

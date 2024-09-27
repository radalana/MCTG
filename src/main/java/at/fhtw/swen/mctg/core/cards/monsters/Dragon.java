package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Monster;

public class Dragon extends Monster {
    public Dragon(int damage, Element type) {
        super(damage, type);
    }

    @Override
    public void attack() {
        System.out.println("Breath fire");
    }

}

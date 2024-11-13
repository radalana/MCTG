package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Dragon extends Monster {
    public Dragon(String id, String name, double damage, Element type) {

        super(id, name, damage, type, MonsterType.DRAGON);
    }
    @Override
    public void attack() {
        System.out.println("Breath fire");
    }

}

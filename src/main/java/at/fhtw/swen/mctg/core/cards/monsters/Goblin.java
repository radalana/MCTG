package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Goblin extends Monster {

    public Goblin(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.GOBLIN);
    }
    @Override
    public void attack() {
        System.out.println("Stab with a rusty dagger");
    }
}

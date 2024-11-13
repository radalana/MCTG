package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Wizard extends Monster {
    public Wizard(String id, String name, double damage, Element element) {

        super(id, name, damage, element, MonsterType.WIZARD);
    }
    @Override
    public void attack() {
        System.out.println("Casts a fireball");
    }
}

package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Ork extends Monster {
    public Ork(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.ORK);
    }
    @Override
    public void attack() {
        System.out.println("Swings a giant axe");
    }
}

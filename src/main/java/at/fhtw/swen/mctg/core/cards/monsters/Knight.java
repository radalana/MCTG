package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Knight extends Monster {
    public Knight(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.KNIGHT);
    }
    public Knight(double damage) {super(damage);}
    @Override
    public void attack() {
        System.out.println("Strikes with a sword");
    }
}

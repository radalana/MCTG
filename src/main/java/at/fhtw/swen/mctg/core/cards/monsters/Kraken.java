package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

import java.lang.annotation.ElementType;

public class Kraken extends Monster {
    public Kraken (String id, String name, double damage, Element element) {

        super(id, name, damage, element, MonsterType.KRAKEN);
    }

    @Override
    public void attack() {
        System.out.println("Smashes with tentacles");
    }


}

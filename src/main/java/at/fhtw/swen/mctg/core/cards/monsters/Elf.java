package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;

public class Elf extends Monster {
    public Elf(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.ELF);
    }
    @Override
    public void attack() {
        System.out.println("Shoots an arrow");
    }
}

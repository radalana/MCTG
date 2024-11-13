package at.fhtw.swen.mctg.core.cards.factories;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.core.cards.monsters.*;
import at.fhtw.swen.mctg.model.Card;

public class MonsterCardFactory {
    public static Monster createMonster(String id, String name, double damage, Element element, MonsterType monsterType) {
        return switch (monsterType) {
            case GOBLIN-> new Goblin(id, name, damage, element);
            case DRAGON -> new Dragon(id, name, damage, element);
            case ORK -> new Ork(id, name, damage, element);
            case ELF -> new Elf(id, name, damage, element);
            case KNIGHT -> new Knight(id, name, damage, element);
            case KRAKEN -> new Kraken(id, name, damage, element);
            case WIZARD -> new Wizard(id, name, damage, element);
        };
    }
}

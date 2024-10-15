package at.fhtw.swen.mctg.core.cards.factories;

import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.core.cards.monsters.*;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;

public class CardFactory {
    private Card createMonster(String monsterType, double damage, Card.Element element ) {
        return switch (monsterType) {
            case "Goblin" -> element == null ? new Goblin(damage) : new Goblin(damage, element);
            case "Dragon" -> element == null ? new Dragon(damage) : new Dragon(damage, element);
            case "Ork" -> element == null ? new Ork(damage) : new Ork(damage, element);
            case "Elf" -> element == null ? new Elf(damage) : new Elf(damage, element);
            case "Knight" -> element == null ? new Knight(damage) : new Knight(damage, element);
            case "Kraken" -> element == null ? new Kraken(damage) : new Kraken(damage, element);
            case "Wizard" -> element == null ? new Wizard(damage) : new Wizard(damage, element);
            default -> throw new IllegalArgumentException("Unknown monster type: " + monsterType);
        };
    }
    private Card.Element getElement(String string) {
            //Exception if string null or empty
            return Card.Element.valueOf(string.toUpperCase());
    }

    /*
        Creates a specific card (either a monster or a spell) based on the provided name and damage.
    */
    public Card createCard(CardData data) {
        double damage = data.getDamage();
        String name = data.getName();
        String[] parts = name.split("(?=[A-Z])");//"GoblinWater" in "Goblin" "water"
        String monsterType;

        if (parts.length == 1) {
            //only monster without element
            monsterType = parts[0];
            return createMonster(monsterType, damage, null);
        }else {
            Card.Element element = getElement(parts[0]);
            if (parts[1].equals("Spell")) {
                return new Spell(damage, element);
            }else {
                monsterType = parts[1];
                return createMonster(monsterType, damage, element);
            }
        }
    };
}

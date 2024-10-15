package at.fhtw.swen.mctg.core.cards.factories;

import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.core.cards.monsters.*;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;

public class CardFactory {
    private Card.Element getElement(String string) {
            //Exception if string null or empty
            return Card.Element.valueOf(string.toUpperCase());
    }
//0 - елемент, 1 монстр карта
    public Card createCard(CardData data) {
        double damage = data.getDamage();
        String name = data.getName();
        String[] parts = name.split("(?=[A-Z])");//"GoblinWater" in "Goblin" "water"
        if (parts.length == 1) {
            String monstrType = parts[0];
            //only monster without element
            //searchin only monster
            switch (monstrType) {
                case "Goblin":
                    return new Goblin(damage);
                case "Dragon":
                    return new Dragon(damage);
                case "Ork":
                    return new Ork(damage);
                case "Elf":
                    return new Elf(damage);
                case "Knight":
                    return new Knight(damage);
                case "Kraken":
                    return  new Kraken(damage);
                case "Wizard":
                    return new Wizard(damage);
                default:
                    throw new IllegalArgumentException("Unknown monster type: " + monstrType);
            }
        }else {
            //spell or monster parts[0]
            //element part[1]
            Card.Element element = Card.Element.valueOf(parts[0].toUpperCase());
            if (parts[1].equals("Spell")) {
                return new Spell(damage, element);
            }else {
                switch (parts[1]) {
                    case "Goblin":
                        return new Goblin(damage, element);
                    case "Dragon":
                        return new Dragon(damage, element);
                    case "Ork":
                        return new Ork(damage, element);
                    case "Elf":
                        return new Elf(damage, element);
                    case "Knight":
                        return new Knight(damage, element);
                    case "Kraken":
                        return  new Kraken(damage, element);
                    case "Wizard":
                        return new Wizard(damage, element);
                    default:
                        throw new IllegalArgumentException("Unknown monster type: " + parts[1]);
                }
            }
        }
    };
}

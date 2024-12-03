package at.fhtw.swen.mctg.core.cards.factories;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;

public class CardFactory {
    // Erstellt eine Karte basierend auf ihrem Namen (Typ, Element, etc.)
    //durch Parsing aus dem HTTP-Body, beim Package Erstellung
    public Card createCardFromName(CardData data) {
        String id = data.getId();
        double damage = data.getDamage();
        String name = data.getName();
        String[] parts = name.split("(?=[A-Z])");//"GoblinWater" in "Goblin" "water"
        MonsterType  monsterType;

        if (parts.length == 1) {
            //only monster without element
            monsterType = MonsterType.fromString(parts[0]);
            return MonsterCardFactory.createMonster(id, name, damage, null, monsterType);
        }else {
            Element element = Element.fromString(parts[0]);
            if (parts[1].equals("Spell")) {
                return new Spell(id, name, damage, element);
            }else {
                monsterType = MonsterType.fromString(parts[1]);
                return MonsterCardFactory.createMonster(id, name, damage, element, monsterType);
            }
        }
    }

    public Card createCard(CardData data) {
        String id = data.getId();
        String name = data.getName();
        double damage = data.getDamage();
        String type = data.getType();
        String subType = data.getSubType();
        Element element = Element.fromString(data.getElement());
        switch (type) {
            case "spell":
                return new Spell(id, name, damage, element);
            case "monster":
                if (subType == null) {
                    //TODO is argument exception right here?
                    throw new IllegalArgumentException("Monster type cannot be null");
                }
                MonsterType monsterType = MonsterType.fromString(subType);
                return MonsterCardFactory.createMonster(id, name, damage, element, monsterType);
            default:
                throw new IllegalArgumentException("Unsupported card type: " + type);
        }
    }
}

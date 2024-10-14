package at.fhtw.swen.mctg.core.cards.factories;

import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.core.cards.monsters.Dragon;
import at.fhtw.swen.mctg.core.cards.monsters.Goblin;
import at.fhtw.swen.mctg.core.cards.monsters.Ork;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;

public class CardFactory {
    private Card.Element getElement(String string) {
            //Exception if string null or empty
            return Card.Element.valueOf(string.toUpperCase());
    }

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
                case "Elf"
                case "Knight"
                case "Kraken"
                case "Wizard"
            }
        }else {
            //spell or monster parts[0]
            //element part[1]
        }
    };
}

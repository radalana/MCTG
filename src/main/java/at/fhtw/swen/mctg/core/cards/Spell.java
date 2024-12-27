package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.cards.monsters.Knight;
import at.fhtw.swen.mctg.core.cards.monsters.Kraken;
import at.fhtw.swen.mctg.model.Card;

public class Spell extends Card {

    public Spell(String id, String name, double damage, Element element) {
        super(id, name, damage, element);
    }
    @Override
    public int fight(Card opponent) {
        if (getElement() == Element.WATER) {
            if (opponent instanceof Knight) {
                return 1;
            }
        }

        if (opponent instanceof Kraken) {
            return -1;
        }
        System.out.println("Spell");
        return super.fight(opponent);
    }
    @Override
    public boolean isMonsterType() {
        return false;
    }
}

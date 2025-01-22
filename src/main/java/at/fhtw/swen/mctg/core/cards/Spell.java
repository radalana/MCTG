package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.cards.monsters.Knight;
import at.fhtw.swen.mctg.core.cards.monsters.Kraken;
import at.fhtw.swen.mctg.model.Card;

public class Spell extends Card {

    public Spell(String id, String name, double damage, Element element) {
        super(id, name, damage, element);
    }
    @Override
    public int fight(Card opponent, double effectiveness) {
        if (!isNoRulesMode(opponent)) {
            if (getElement() == Element.WATER) {
                if (opponent instanceof Knight) {
                    return 1;
                }
            }

            if (opponent instanceof Kraken) {
                return -1;
            }
        }
        return super.fight(opponent, effectiveness);
    }
    @Override
    public boolean isMonsterType() {
        return false;
    }
}

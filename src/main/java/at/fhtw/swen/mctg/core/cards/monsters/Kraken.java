package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;

public class Kraken extends Monster {
    public Kraken (String id, String name, double damage, Element element) {

        super(id, name, damage, element, MonsterType.KRAKEN);
    }

    @Override
    public int fight(Card opponent) {
        if (opponent instanceof Spell) {
            System.out.println("is immune against " + opponent.getName());
            return 1;
        }
        System.out.println("Smashes with tentacles");
        return super.fight(opponent);
    }


}

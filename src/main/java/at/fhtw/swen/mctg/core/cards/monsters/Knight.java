package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;

public class Knight extends Monster {
    public Knight(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.KNIGHT);
    }

    @Override
    public int fight(Card opponent, double effectiveness) {
        if (opponent instanceof Spell && opponent.getElement() == Element.WATER) {
            System.out.println("Knight was drown");
            return -1;
        }
        System.out.println("Strikes with a sword");
        return super.fight(opponent, effectiveness);
    }
}

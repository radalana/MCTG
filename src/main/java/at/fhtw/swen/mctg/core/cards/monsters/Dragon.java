package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.model.Card;


public class Dragon extends Monster {
    public Dragon(String id, String name, double damage, Element type) {

        super(id, name, damage, type, MonsterType.DRAGON);
    }
    @Override
    public int fight(Card opponent) {
        if (opponent instanceof Goblin) {
            return 1;
        }
        if ((opponent instanceof Elf) && (opponent.getElement() == Element.FIRE)) {
            System.out.println("Dragons can not attack Fire Elves, because they raised together");
            return 0;
        }
        System.out.println("Breath fire");
        return super.fight(opponent);
    }

}

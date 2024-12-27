package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.model.Card;

public class Goblin extends Monster {

    public Goblin(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.GOBLIN);
    }
    @Override
    public int fight(Card opponent) {
        //TODO dont need logic of elements bcs both monsters
        if (opponent instanceof Dragon) {
            System.out.println("Is too afraid of Dragon " + opponent.getName());
            return -1;
        }
        System.out.println("Stab with a rusty dagger");
        return Double.compare(getDamage(), opponent.getDamage());
    }
}

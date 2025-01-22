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
    public int fight(Card opponent, double effectiveness) {
        if (opponent instanceof Dragon) {
            System.out.println("Is too afraid of Dragon " + opponent.getName());
            return -1;
        }
        System.out.println(generateAttackMessage());
        return super.fight(opponent, effectiveness);
    }

    @Override
    protected String generateAttackMessage() {
        return switch (this.getElement()) {
            case FIRE -> "Throws an explosive bomb";
            case WATER -> "Splashes dirty swamp water";
            case NORMAL -> "Swings a rusty dagger";
            default -> "Tries to bite the opponent";
        };
    }
}

package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.model.Card;

public class Wizard extends Monster {

    public Wizard(String id, String name, double damage, Element element) {

        super(id, name, damage, element, MonsterType.WIZARD);
    }

    @Override
    public int fight(Card opponent, double effectiveness) {
        if (opponent instanceof Ork) {
            System.out.println("Controls Ork " + opponent.getName());
            return 1;
        }
        System.out.println("Casts a fireball");
        return super.fight(opponent, effectiveness);
    }

    @Override
    protected String generateAttackMessage() {
        return switch (this.getElement()) {
            case FIRE -> "Casts a blazing fireball";
            case WATER -> "Summons a freezing ice storm";
            case NORMAL -> "Unleashes a burst of arcane energy";
            default -> "Channels an ancient spell of destruction";
        };
    }

}

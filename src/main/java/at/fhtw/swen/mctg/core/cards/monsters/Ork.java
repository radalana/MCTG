package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.model.Card;

public class Ork extends Monster {
    public Ork(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.ORK);
    }
    @Override
    public int fight(Card opponent, double effectiveness) {
        System.out.println("Swings a giant axe");
        if (opponent instanceof Wizard) {
            System.out.println("Ork is controlled by wizard");
            return -1;
        }
        return super.fight(opponent, effectiveness);
    }

    @Override
    protected String generateAttackMessage() {
        return switch (this.getElement()) {
            case FIRE -> "Swings a flaming axe";
            case WATER -> "Throws a massive water-infused boulder";
            case NORMAL -> "Smashes the opponent with brute force";
            default -> "Attacks with wild fury";
        };
    }
}

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
    public int fight(Card opponent, double effectiveness) {
        if (isNoRulesMode(opponent)) {
            return super.fight(opponent, effectiveness);
        }
        if (opponent instanceof Spell) {
            System.out.println(this.getName() + " is immune against " + opponent.getName());
            return 1;
        }
        System.out.println(generateAttackMessage());
        return super.fight(opponent, effectiveness);
    }

    @Override
    protected String generateAttackMessage() {
        Element element = this.getElement();
        if (element == null) {
            return "Wraps the opponent in an unbreakable grip";
        }
        return switch (element) {
            case FIRE -> "Spews a jet of boiling steam";
            case WATER -> "Unleashes a massive tidal wave";
            case NORMAL -> "Crushes the opponent with giant tentacles";
        };
    }


}

package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.model.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static at.fhtw.swen.mctg.core.cards.Element.FIRE;
import static at.fhtw.swen.mctg.core.cards.Element.WATER;

public class Dragon extends Monster {
    public Dragon(String id, String name, double damage, Element type) {

        super(id, name, damage, type, MonsterType.DRAGON);
    }
    @JsonIgnore
    protected double getRandomChance() {
        return Math.random();
    }
    @Override
    public int fight(Card opponent, double effectiveness) {
        if (isNoRulesMode(opponent)) {
            System.out.println("No rules");
            return super.fight(opponent, effectiveness);
        }
        if (opponent instanceof Goblin) {
            return 1;
        }

        //Dragons against FIRE elves
        if ((opponent instanceof Elf) && (opponent.getElement() == FIRE)) {
            //Fire dragon against Fire Elf
            if (this.getElement() == FIRE) {
                System.out.println("Fire Dragons can not attack Fire Elves, because they raised together");
                return 0;
            }
            //if Dragons are not fire, elves can evade attack only in 50%
            if (getRandomChance() < 0.5) {
                System.out.println("FireElves try to evade the non-fire Dragon, and succeed!");
                return 0;
            }
        }
        System.out.println(generateAttackMessage());
        return super.fight(opponent, effectiveness);
    }

    @Override
    public String generateAttackMessage() {
        Element element = this.getElement();
        return switch (element) {
            case FIRE -> "Breath fire";
            case WATER -> "Breath boiled water";
            case NORMAL -> "Unleash a mighty roar";
            default -> "Attacks fiercely";
        };
    }

}

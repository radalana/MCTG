package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.MonsterType;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;

public class Elf extends Monster {
    public Elf(String id, String name, double damage, Element element) {
        super(id, name, damage, element, MonsterType.ELF);
    }
    protected double getRandomChance() {
        return Math.random();
    }
    @Override
    public int fight(Card opponent, double effectiveness) {
        if (isNoRulesMode(opponent)) {
            System.out.println("No rules");
            return super.fight(opponent, effectiveness);
        }
        if (this.getElement() == Element.FIRE && opponent instanceof Dragon) {
            Dragon dragon = (Dragon) opponent;
            if (dragon.getElement() == Element.FIRE) {
                System.out.println("Fire elf can not be attacked by fire dragons");
                return 0;
            }else {
                //if Dragons are not fire, elves can evade attack only in 50%
                if (getRandomChance() >= 0.5) {
                    System.out.println("FireElves try to evade the non-fire Dragon, and succeed!");
                    return 0;
                }else {
                    System.out.println("FireElves try to evade the non-fire Dragon, but fail!");
                    return -1;
                }
            }
        }

        if (this.getElement() == Element.WATER && opponent instanceof Spell && opponent.getElement() == Element.WATER) {
            Spell spell = (Spell) opponent;
            // water spell is stronger than elf, elf can fend off the attack
            if (Double.compare(spell.getDamage(), this.getDamage()) > 0) {
                return 0;
            }
        }
        System.out.println(generateAttackMessage());
        return super.fight(opponent, effectiveness);
    }

    @Override
    protected String generateAttackMessage() {
        return switch (this.getElement()) {
            case FIRE -> "Shoots a blazing arrow";
            case WATER -> "Summons a wave of water magic";
            case NORMAL -> "Strikes swiftly with an enchanted blade";
            default -> "Attacks with elven precision";
        };
    }
}

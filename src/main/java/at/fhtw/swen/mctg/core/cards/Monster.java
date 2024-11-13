package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;
import lombok.Getter;

@Getter
public abstract class Monster extends Card {
    private MonsterType subtype;
    public Monster(String id, String name, double damage,  Element element, MonsterType subtype) {
        super(id, name, damage, element);
        this.subtype = subtype;
    }

    public Monster(double damage, Element type) {
        super(damage, type);
    }
    public Monster(double damage) {
        super(damage);
    }

    @Override
    public boolean isMonsterType() {
        return true;
    }
}

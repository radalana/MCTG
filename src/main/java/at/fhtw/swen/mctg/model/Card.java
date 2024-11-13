package at.fhtw.swen.mctg.model;


import at.fhtw.swen.mctg.core.cards.Element;
import lombok.Getter;

@Getter
public abstract class Card {
    private String id;
    private String name;
    private final double damage;
    private Element element;
    public Card(String id, String Name, double Damage, Element element) {
        this.id = id;
        this.name = Name;
        this.damage = Damage;
        this.element = element;
    }
    public Card(double damage, Element type) {
        this.damage = damage;
        element = type;
    }
    public Card(double damage) {
        this.damage = damage;
    }
    public abstract void attack();
    public abstract boolean isMonsterType();

    @Override
    public String toString() {
        return "\nid: " + this.id + "\nname:" + this.name +  "\nclass: " + this.getClass().getSimpleName() + "\ndamage: " + damage + "\nelement: " + this.element;
    }

}

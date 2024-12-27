package at.fhtw.swen.mctg.model;


import at.fhtw.swen.mctg.core.ElementFactors;
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

    public int fight(Card opponent){
        if (this == opponent) {
            throw new IllegalArgumentException("Cannot fight the same card");
        }
        //System.err.println("I am in card fight");
        //TODO logic if card against itself - exception
        Element opponentElement = opponent.getElement();
        double effectiveness = ElementFactors.getMultiplier(element, opponentElement);
        return Double.compare(getDamage()*effectiveness, opponent.getDamage());
    }
    public abstract boolean isMonsterType();

    @Override
    public String toString() {
        return "\nid: " + this.id + "\nname:" + this.name +  "\nclass: " + this.getClass().getSimpleName() + "\ndamage: " + damage + "\nelement: " + this.element;
    }

}

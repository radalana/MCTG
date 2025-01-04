package at.fhtw.swen.mctg.model;


import at.fhtw.swen.mctg.core.ElementFactors;
import at.fhtw.swen.mctg.core.cards.Element;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Card {
    private String id;
    private String name;
    private final double damage;
    private Element element = null;
    @Setter
    private String ownerName;
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

    public int fight(Card opponent, double effectiveness){
        System.err.println("---In abstract class Card.fight------");
        if (opponent == null) {
            System.err.println("Opponent is null");
        }
        System.err.println("this.hashCode:" + this.hashCode());
        System.err.println("opponent.hashCode:" + opponent.hashCode());
        if (this == opponent) {
            throw new IllegalArgumentException("Cannot fight the same card");
        }
        //System.err.println("I am in card fight");
        //TODO logic if card against itself - exception
        //Element opponentElement = opponent.getElement();
        //double effectiveness = ElementFactors.getMultiplier(element, opponentElement);
        System.out.println("effectiveness: " + effectiveness);
        return Double.compare(getDamage()*effectiveness, opponent.getDamage());
    }

    public String getElementAsString() {
        if (element == null) {
            return "no element";
        }
        return element.getValue();
    }

    public abstract boolean isMonsterType();

    @Override
    public String toString() {
        return "\nid: " + this.id + "\nname:" + this.name +  "\nclass: " + this.getClass().getSimpleName() + "\ndamage: " + damage + "\nelement: " + this.element;
    }

}

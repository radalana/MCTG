package at.fhtw.swen.mctg.model;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

public abstract class Card {
    public enum Element {
        FIRE,
        WATER,
        NORMAL
    }

    private String Id;
    private String Name;
    private final int Damage;
    private Element element;
    public Card(String id, String Name, int Damage) {
        this.Id = id;
        this.Name = Name;
        this.Damage = Damage;
    }
    public Card(int damage, Element type) {
        this.Damage = damage;
        element = type;
    }
    public abstract void attack();
    public abstract boolean isMonsterType();

    public int getDamage() {
        return Damage;
    }

}

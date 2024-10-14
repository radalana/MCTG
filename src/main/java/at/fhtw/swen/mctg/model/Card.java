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
    private final double Damage;
    private Element element;
    private String type;//TODO smth with enum
    public Card(String id, String Name, double Damage) {
        this.Id = id;
        this.Name = Name;
        this.Damage = Damage;
    }
    public Card(double damage, Element type) {
        this.Damage = damage;
        element = type;
    }
    public Card(double damage) {
        this.Damage = damage;
    }
    public abstract void attack();
    public abstract boolean isMonsterType();

    public double getDamage() {
        return Damage;
    }

}

package at.fhtw.swen.mctg.core;


public abstract class Card {
    public enum Element {
        FIRE,
        WATER,
        NORMAL
    }
    private String name;
    private final int damage;
    private Element element;
    public Card(int damage, Element type) {
        this.damage = damage;
        element = type;
    }
    public abstract void attack();
    public abstract boolean isMonsterType();

    public int getDamage() {
        return damage;
    }

}

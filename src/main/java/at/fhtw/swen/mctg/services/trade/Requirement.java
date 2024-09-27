package at.fhtw.swen.mctg.services.trade;

public class Requirement {
    public enum CardType{
        MONSTER,
        SPELL
    }
    private CardType type;
    private int minDamage;

    public Requirement(CardType type, int minDamage) {
        this.type = type;
        this.minDamage = minDamage;
    }

}

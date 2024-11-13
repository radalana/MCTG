package at.fhtw.swen.mctg.core.cards;

public enum MonsterType {
    ELF,
    WIZARD,
    DRAGON,
    GOBLIN,
    ORK,
    KRAKEN,
    KNIGHT;

    public static MonsterType fromString(String value) {
        for (MonsterType subtype : MonsterType.values()) {
            if (subtype.name().equalsIgnoreCase(value)) {
                return subtype;
            }
        }
        throw new IllegalArgumentException("Unknown monster subtype: " + value);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

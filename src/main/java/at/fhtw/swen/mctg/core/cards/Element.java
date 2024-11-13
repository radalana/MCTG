package at.fhtw.swen.mctg.core.cards;

import lombok.Getter;

@Getter
public enum Element {
    WATER("water"),
    FIRE("fire"),
    NORMAL("normal");

    private final String value;
    Element(String value) {
        this.value = value;
    }

    public static Element fromString(String value) {
        for (Element element : Element.values()) {
            if (element.value.equalsIgnoreCase(value)) {
                return element;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

}

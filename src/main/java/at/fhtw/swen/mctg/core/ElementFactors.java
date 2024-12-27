package at.fhtw.swen.mctg.core;

import at.fhtw.swen.mctg.core.cards.Element;

import java.util.Map;

public class  ElementFactors {
    private static final Map<Element, Map<Element, Double>> elementsFactors;

    static {
        elementsFactors = Map.of(
                Element.WATER, Map.of(
                        Element.FIRE, 2.0,
                        Element.NORMAL, 0.5,
                        Element.WATER, 1.0
                ),
                Element.FIRE, Map.of(
                        Element.NORMAL, 2.0,
                        Element.WATER, 0.5,
                        Element.FIRE, 1.0
                ),
                Element.NORMAL, Map.of(
                        Element.WATER, 2.0,
                        Element.FIRE, 0.5,
                        Element.NORMAL, 1.0
                )
        );
    }

    public static double getMultiplier(Element attacker, Element defender) {
        return elementsFactors.getOrDefault(attacker, Map.of())
                .getOrDefault(defender, 1.0);
    }
}

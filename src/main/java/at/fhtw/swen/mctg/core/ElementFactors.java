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
    //TODO logic if card does not have any element
    public static double getMultiplier(Element element1, Element element2) {
        System.err.println("------getMultiplier--------");
        if (element1 != null && element2 != null) {
            return elementsFactors.get(element1).get(element2);
        }
        //if card does not have element, it is weaker all others cards with elements
        if (element1 == null) {
            return  1/3.0;
        }
        return 3.0;
    }
}

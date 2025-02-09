package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.ElementFactors;
import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class KrakenTest {

    @Test
    @DisplayName("The Kraken is immune against spells.")
    void testFightKrakenVsSpell() {
        Card kraken = new Kraken("kraken", "kraken", 1.0, Element.NORMAL);
        Card fireSpell = new Spell("fireSpell", "fireSpell", 1.0, Element.FIRE);

        //Fire element is stronger than Normal element, but Kraken is immune to all spells
        double effectiveness = ElementFactors.getMultiplier(kraken.getElement(), fireSpell.getElement());
        assertEquals(1, kraken.fight(fireSpell, effectiveness));
        assertEquals(-1, fireSpell.fight(kraken, effectiveness));
    }

    @Test
    @DisplayName("No special rules - kraken doesn't have elemtne")
    void testKrakenWithNoElement() {
        Kraken kraken = new Kraken("kraken", "kraken", 1.0, null);
        Spell waterSpell = new Spell("waterSpell", "waterSpell", 5.0, Element.WATER);
        assertEquals(-1, kraken.fight(waterSpell, 1.0));
        assertEquals(1, waterSpell.fight(kraken, 1.0));
    }
}
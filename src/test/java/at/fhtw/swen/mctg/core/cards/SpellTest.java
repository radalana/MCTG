package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.ElementFactors;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
class SpellTest {

    @Test
    void testFightNormalVsNormalSpell() {
        Card normalSpell1 = new Spell("NormSpell1", "NormSpell1", 0.1, Element.NORMAL);
        Card normalSpell2 = new Spell("NormSpell2", "NormSpell2", 0.1, Element.NORMAL);
        double effectivenessNormalVsNormal = ElementFactors.getMultiplier(normalSpell1.getElement(), normalSpell2.getElement());;
        assertEquals(1.0, effectivenessNormalVsNormal, 0.01);
        assertEquals(0, normalSpell1.fight(normalSpell2, effectivenessNormalVsNormal));
        assertEquals(0, normalSpell2.fight(normalSpell1, effectivenessNormalVsNormal));
    }
}
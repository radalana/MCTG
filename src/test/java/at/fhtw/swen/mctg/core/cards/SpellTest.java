package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.core.ElementFactors;
import at.fhtw.swen.mctg.core.cards.monsters.Knight;
import at.fhtw.swen.mctg.core.cards.monsters.Kraken;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
class SpellTest {

    @Test
    @DisplayName("Spell can not attack krakens")
    void testSpellVsKraken() {
       Spell fireSpell = new Spell("fireSpell", "fireSpell", 10.0, Element.FIRE);
       Spell waterSpell = new Spell("waterSpell", "waterSpell", 10.0, Element.WATER);
       Spell normalSpell = new Spell("normalSpell", "normalSpell", 10.0, Element.NORMAL);

       Kraken kraken = new Kraken("kraken", "kraken", 1.0, Element.NORMAL);
       int result1 = fireSpell.fight(kraken, 1.0);
       int result2 = waterSpell.fight(kraken, 1.0);
       int result3 = normalSpell.fight(kraken, 1.0);
       assertEquals(-1, result1);
       assertEquals(-1, result2);
       assertEquals(-1, result3);
    }

    @Test
    @DisplayName("Water spell wins Knight")
    void testWaterSpellVsKnight() {
        Spell waterSpell = new Spell("waterSpell", "waterSpell", 1.0, Element.WATER);
        Knight knight = new Knight("knight", "knight", 10.0, Element.WATER);

        int actual = waterSpell.fight(knight, 1.0);
        assertEquals(1, actual);
    }
}
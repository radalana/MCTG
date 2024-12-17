package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class KnightTest {

    @Test
    @DisplayName("The armor of Knights is so heavy that WaterSpells make them drown them instantly.")
    void fight() {
        Card knight = new Knight("knight", "knight", 5.0, Element.NORMAL);
        Card waterSpell = new Spell("waterSpell", "waterSpell", 1.0, Element.WATER);
        assertEquals(-1, knight.fight(waterSpell));
        assertEquals(1, waterSpell.fight(knight));
    }
}
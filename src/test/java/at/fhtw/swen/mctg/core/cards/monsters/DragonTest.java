package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class DragonTest {

    @Test
    @DisplayName("FireElves can evade dragon attacks")
    void fightDragonVsFireElves() {
        Card dragon = new Dragon("dragon", "dragon", 5.0, Element.WATER);
        Card fireElf = new Elf("fire elf", "fire elf", 1.0, Element.FIRE);

        assertEquals(0, dragon.fight(fireElf, 1.0));
        assertEquals(0, fireElf.fight(dragon, 1.0));
    }
}
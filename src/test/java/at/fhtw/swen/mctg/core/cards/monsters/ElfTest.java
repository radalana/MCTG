package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;

class ElfTest {
    @Test
    @DisplayName("Fire Elf against Fire Dragon")
    void fireElfAgainstFireDragon() {
        Elf fireElf = new Elf("fireElf", "fireElf", 1.0, Element.FIRE);
        Dragon fireDragon = new Dragon("fireDragon", "fireDragon", 5.0, Element.FIRE);
        int actual = fireElf.fight(fireDragon, 1.0);
        assertEquals(0, actual);
    }
    @Test
    @DisplayName("Not Fire Elf against Fire Dragon")
    void notFireElfAgainstFireDragon() {
        Elf waterElf = new Elf("waterElf", "waterElf", 1.0, Element.WATER);
        Dragon fireDragon = new Dragon("fireDragon", "fireDragon", 5.0, Element.FIRE);

        int actual = waterElf.fight(fireDragon, 1.0);
        assertEquals(-1, actual);
    }

    @Test
    @DisplayName("Water Elves with lower damage do not lose to Water spells (draw)")
    void waterElvesVsWaterSpell() {
        Elf waterElf = new Elf("waterElf", "waterElf", 1.0, Element.WATER);
        Spell waterSpell = new Spell("waterSpell", "waterSpell", 5.0, Element.WATER);

        int actual = waterElf.fight(waterSpell, 1.0);
        assertEquals(0, actual);
    }




}
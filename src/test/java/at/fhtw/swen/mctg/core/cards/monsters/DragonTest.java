package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DragonTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    @DisplayName("FireElves can evade fire dragon attacks")
    void fightDragonVsFireElves() {
        Card dragon = new Dragon("dragon", "dragon", 5.0, Element.FIRE);
        Card fireElf = new Elf("fire elf", "fire elf", 1.0, Element.FIRE);

        int actual = dragon.fight(fireElf, 1.0);
        assertEquals(0, actual);
        assertEquals("Fire Dragons can not attack Fire Elves, because they raised together\n", outContent.toString());
    }

    @Test
    @DisplayName("No special rules if one of the card doesn't have any element")
    void fightWithoutElementFirElf() {
        Dragon dragon = new Dragon("dragon", "dragon", 5.0, null);
        Elf fireElf = new Elf("fire elf", "fire elf", 1.0, Element.FIRE);
        int actual = dragon.fight(fireElf, 1.0);
        assertEquals(1, actual);
    }

    @Test
    @DisplayName("FireElves can evade not-fire dragon attacks in 50%")
    void fightDragonVsNotFireElves() {
        Card fireElf = new Elf("fire elf", "fire elf", 1.0, Element.FIRE);

        //Dragon could not attack fire elf
        Dragon waterDragon = mock(Dragon.class);
        when(waterDragon.getElement()).thenReturn(Element.WATER);
        when(waterDragon.getRandomChance()).thenReturn(0.3);
        when(waterDragon.getDamage()).thenReturn(10.0);
        assertEquals(0, waterDragon.fight(fireElf, 1.0));

        //Normal Dragon attacked fire elf
        Dragon normalDragon = mock(Dragon.class);
        when(normalDragon.getElement()).thenReturn(Element.NORMAL);
        when(normalDragon.getRandomChance()).thenReturn(0.7);
        when(normalDragon.getDamage()).thenReturn(10.0);
        when(normalDragon.fight(any(Card.class), anyDouble())).thenCallRealMethod();
        int actual = normalDragon.fight(fireElf, 1.0);

        assertEquals(1, actual);
    }
    @Test
    @DisplayName("Dragons win against Goblins unless Goblin has higher damage (then draw)")
    void fightDragonVsGoblin() {
        Dragon waterDragon = new Dragon("dragon", "dragon", 5.0, Element.WATER);
        Goblin normalGoblin = new Goblin("goblin", "goblin", 10.0, Element.FIRE);

        int actual = waterDragon.fight(normalGoblin, 1.0);
        assertEquals(1, actual);
    }

    @Test
    @DisplayName("Dragons win against Goblins unless Goblin has higher damage (then draw)")
    void fightDragonVsGoblinNoElement() {
        Dragon waterDragon = new Dragon("dragon", "dragon", 5.0, Element.FIRE);
        Goblin goblin = new Goblin("goblin", "goblin", 10.0, null);

        int actual = waterDragon.fight(goblin, 1.0);
        assertEquals(-1, actual);
    }

}
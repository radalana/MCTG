package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class GoblinTest {

    @Test
    @DisplayName("Goblin can not attack dragons")
    void testFightGoblinVsDragon() {
        Card goblin = new Goblin("goblin", "goblin", 5.0, Element.NORMAL);
        Card dragon = new Dragon("dragon", "dragon", 1.0, Element.FIRE);

        assertEquals(-1, goblin.fight(dragon, 1.0));
    }
}
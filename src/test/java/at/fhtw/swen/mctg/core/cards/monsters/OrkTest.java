package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
class OrkTest {
  //Test against Wizard
  @Test
  @DisplayName("Ork is not able to damage Wizard")
  void testFightOrkVsWizard() {
      Card ork = new Ork("ork", "ork", 5.0, Element.NORMAL);
      Card wizard = new Wizard("wizard", "wizard", 1.0, Element.NORMAL);

      assertEquals(-1, ork.fight(wizard));
      assertEquals(1, wizard.fight(ork));
  }

  //Test fight against other Monster, that Elements does not effect
  @Test
  @DisplayName("Ork is not able to damage Wizard")
  void testFightFireOrkVsWaterMonster() {
      Card ork = new Ork("ork", "ork", 1.5, Element.FIRE);
      Card kraken = new Kraken("kraken", "kraken", 1.0, Element.WATER);

      assertEquals(1, ork.fight(kraken));
      assertEquals(-1, kraken.fight(ork));
  }
  //Test against Spell
  @Test
  @DisplayName("Fire Ork (1.0) vs Water Spell (1.0) -> Spell wins")
  void testFightFireOrkVsWaterSpell() {
      Card ork = new Ork("ork", "ork", 1.0, Element.FIRE);
      Card water = new Spell("spell", "water", 1.0, Element.WATER);

      assertEquals(-1, ork.fight(water));
      assertEquals(1, water.fight(ork));
  }
}
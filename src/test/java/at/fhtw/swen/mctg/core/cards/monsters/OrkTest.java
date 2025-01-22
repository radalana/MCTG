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

      assertEquals(-1, ork.fight(wizard, 1.0));
      assertEquals(1, wizard.fight(ork, 1.0));
  }

  //Test fight against other Monster, that Elements does not effect

}
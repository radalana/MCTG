package at.fhtw.swen.mctg.core.cards.monsters;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
class WizardTest {

    @Test
    void testFightWizardVsWizardNormal() {
        Wizard wizardA = new Wizard("a", "WizardA", 10.0, Element.NORMAL);
        Wizard wizardB = new Wizard("b", "WizardB", 1.0, Element.NORMAL);
        assertEquals(1, wizardA.fight(wizardB));
        assertEquals(-1, wizardB.fight(wizardA));
    }

    @Test
    @DisplayName("No element effect in pure monster fight")
    void testFightWizardWaterVsWizardFire() {
        Wizard wizardA = new Wizard("a", "WizardA", 1.0, Element.WATER);
        Wizard wizardB = new Wizard("b", "WizardB", 1.0, Element.FIRE);
        assertEquals(0, wizardA.fight(wizardB));
        assertEquals(0, wizardB.fight(wizardA));
    }

    @Test
    @DisplayName("Wizard can control Orks, Wizard(1) wins Ork(100)")
    void testFightWizardOrks() {
        Wizard wizard = new Wizard("Wizard", "Wizard", 1.0, Element.NORMAL);
        Ork ork = new Ork("Ork", "Ork", 100.0, Element.NORMAL);
        assertEquals(1, wizard.fight(ork));
        assertEquals(-1, ork.fight(wizard));
    }

    @Test
    @DisplayName("Wizard fights with monster of another type - knight")
    void testFightWizardKnight() {
        Card wizard = new Wizard("Wizard", "Wizard", 1.0, Element.NORMAL);
        Card knight = new Knight("Knight", "Knight", 2.0, Element.WATER);
        assertEquals(-1, wizard.fight(knight));
        assertEquals(1, knight.fight(wizard));
    }


    //Wizard vs spells
    @Test
    @DisplayName("Wizard(Normal element, 1.0)  fights against water spell(1.0) -> Wizard wins")

    void testFightWizardWaterSpell() {
        Card wizard = new Wizard("Wizard", "Wizard", 1.0, Element.NORMAL);
        Card water = new Spell("Spell", "WaterSpell", 1.0, Element.WATER);

        assertEquals(1, wizard.fight(water));
        assertEquals(-1, water.fight(wizard));
    }

    @Test
    @DisplayName("Wizard(Normal, 1.0)  fights against FIRE spell(1.0) -> Spell wins")
    void testFightWizardFireSpell() {
        Card wizard = new Wizard("Wizard", "Wizard", 1.0, Element.NORMAL);
        Card fire = new Spell("Spell", "WaterSpell", 1.0, Element.FIRE);

        assertEquals(-1, wizard.fight(fire));
        assertEquals(1, fire.fight(wizard));
    }

    @Test
    @DisplayName("Wizard(Normal, 1.0)  fights against Normal spell(1.0) -> draw")
    void testFightWizardNormalSpell() {
        Card wizard = new Wizard("Wizard", "Wizard", 1.0, Element.NORMAL);
        Card fire = new Spell("Spell", "WaterSpell", 1.0, Element.NORMAL);

        assertEquals(0, wizard.fight(fire));
        assertEquals(0, fire.fight(wizard));
    }
    //WaterWizard vs water, fire, normal
    //FireWizard vs water, fire, normal
    //NormalWizard vs water, fire, normal
}
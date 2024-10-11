package at.fhtw.swen.mctg.services;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.Round;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.core.cards.monsters.*;

import static at.fhtw.swen.mctg.services.RoundService.startRound;
import static org.junit.jupiter.api.Assertions.*;

class RoundServiceTest {
    /*
    * TODO test 2 monsters
    * TODO test 2 monsters same damage
    * TODO test spell vs Monster
    * TODO spells and special cases
    * */
    @org.junit.jupiter.api.Test
    void startRoundTest() {
        Card card1 = new Ork(30, Card.Element.NORMAL);//user1
        Card card2 = new Kraken(33, Card.Element.WATER);//user2
        Card card3 = new Dragon(50, Card.Element.FIRE);
        Card card4 = new Elf(45, Card.Element.WATER);
        Card card5 = new Goblin(15, Card.Element.NORMAL);
        Card card6 = new Knight(10, Card.Element.NORMAL);
        Card card7 = new Wizard(70, Card.Element.NORMAL);
        Card card8 = new Spell(34, Card.Element.WATER);
        Card card9 = new Spell(55, Card.Element.FIRE);//user2
        Card card10 = new Spell(30, Card.Element.WATER);
        //card Kraken 33 vs card Knight 10
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck1.addCards(card1, card2, card3, card4);
        deck2.addCards(card1, card6, card7, card8);

        User user1 = new User("user1", deck1);
        User user2 = new User("user2", deck2);
        Round actualRound = startRound(user1, user2);
        User actualWinner = actualRound.getWinner();
        User actualLooser = actualRound.getLooser();
        Card actualPlayed = actualRound.getPlayedCard();
        User expectedWinner = user1;
        User expectedLooser = user2;
        Card expectedPlayedCard = card6;
        assertAll(
                () -> assertEquals(expectedWinner, actualWinner),
                () -> assertEquals(expectedLooser, actualLooser),
                () -> assertEquals(expectedPlayedCard, actualPlayed)
        );
    }
}
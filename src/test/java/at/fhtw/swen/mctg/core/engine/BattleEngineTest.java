package at.fhtw.swen.mctg.core.engine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Spell;
import at.fhtw.swen.mctg.core.cards.monsters.Wizard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import at.fhtw.swen.mctg.model.*;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
class BattleEngineTest {
    @InjectMocks
    private BattleEngine battleEngine;
    @Mock User user1;
    @Mock Deck deckA;
    @Mock User user2;
    @Mock Deck deckB;
    @Spy
    private RoundEngine roundEngine = new RoundEngine(user1, user2);

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Erstellung des BattleEngine-Objekts und Ersetzung von RoundEngine
        battleEngine = new BattleEngine(user1, user2);
        Field field = BattleEngine.class.getDeclaredField("roundEngine");
        field.setAccessible(true);
        field.set(battleEngine, roundEngine); // Ersetzen des echten Objekts durch einen Spy

        when(user1.getUsername()).thenReturn("user1");
        when(user2.getUsername()).thenReturn("user2");
        when(user1.getDeck()).thenReturn(deckA);
        when(user2.getDeck()).thenReturn(deckB);

    }

    @Test
    @DisplayName("User1 wins all rounds and User2 deck becomes empty")
    void startBattleTest() {
        when(deckA.isEmpty())
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false);
        when(deckA.isEmpty())
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        Card card1 = new Wizard("1", "1", 1.0, Element.NORMAL);
        Card card2 = new Wizard("5", "5", 4.0, Element.NORMAL);
        card1.setOwnerName(user1.getUsername());
        card2.setOwnerName(user2.getUsername());
                doReturn(new Round(card1, card2,  1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .when(roundEngine).run();

        Battle battle = battleEngine.startBattle();
        assertEquals(4, battle.getRounds().size());
        assertEquals(4, battle.getUser1BattleResult().getVictories());
        assertEquals(0, battle.getUser2BattleResult().getVictories());
    }


    @Test
    @DisplayName("User1 gewinnt 5 mal, User2 gewinnt 1 mal, 1 mal unentschieden")
    void startBattleTest_1_round_from_4_is_draw(){
        when(deckA.isEmpty())
                .thenReturn(false) //Before dem 1. round
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false);
        when(deckA.isEmpty())
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                        .thenReturn(true);

        /*
        doReturn(new Round(user1, user2, new Wizard("1", "1", 1.0, Element.NORMAL)))// nach dem 1.Round: user1 - 5 Karten | user2 - 3 Karten
                .doReturn(new Round(user1, user2, new Wizard("2", "2", 1.0, Element.NORMAL)))// nach dem 2.Round: user1 - 6 Karten | user2 - 2 Karten
                .doReturn(new Round(user1, user2, new Wizard("3", "3", 2.0, Element.NORMAL)))// nach dem 3.Round: user1 - 7 Karten | user2 - 1 Karten
                .doReturn(new Round(user2, user1, new Wizard("4", "4", 3.0, Element.NORMAL)))// nach dem 4.Round: user1 - 6 Karten | user2 - 2 Karten
                .doReturn(new Round(null, null, null))//nach 5. Round:  dem user1 - 6 Karten | user2 - 2 Karten
                .doReturn(new Round(user1, user2, new Wizard("4", "4", 4.0, Element.NORMAL)))// nach 6.Round: user1 - 7 Karten | user2 - 1 Karten
                .doReturn(new Round(user1, user2, new Wizard("4", "4", 4.0, Element.NORMAL)))// nach 7.Round: user1 - 8 Karten | user2 - 0 Karten
                .when(roundEngine).run();

         */
        Card card1 = new Wizard("1", "1", 1.0, Element.NORMAL);
        Card card2 = new Wizard("5", "5", 4.0, Element.NORMAL);
        card1.setOwnerName(user1.getUsername());
        card2.setOwnerName(user2.getUsername());
       // card3.setOwnerName(user2.getUsername());
        doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card2, card1, 1.0, user2))
                .doReturn(new Round(card2, card1, 1.0, null,true))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .doReturn(new Round(card1, card2, 1.0, user1))
                .when(roundEngine).run();

        Battle battle = battleEngine.startBattle();
        assertEquals(7, battle.getRounds().size());
        assertEquals(5, battle.getUser1BattleResult().getVictories());
        assertEquals(1, battle.getUser2BattleResult().getVictories());
    }

    @DisplayName("Alle Rounds unentschieden, Battle endet nach 100 Rounds")
    @Test
    void startBattle_max_100_rounds() {
        Card card1 = new Wizard("1", "1", 1.0, Element.NORMAL);
        Card card2 = new Wizard("5", "5", 4.0, Element.NORMAL);
        card1.setOwnerName(user1.getUsername());
        card2.setOwnerName(user2.getUsername());
        when(deckA.isEmpty())
                .thenReturn(false);
        when(deckB.isEmpty())
                .thenReturn(false);

        doReturn(new Round(card1, card2, 1.0, null,true)).when(roundEngine).run();

        Battle battle = battleEngine.startBattle();
        assertEquals(100, battle.getNumberOfRounds());
    }

}
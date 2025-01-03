package at.fhtw.swen.mctg.core.engine;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.monsters.Ork;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.Round;
import at.fhtw.swen.mctg.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundEngineTest {
    @InjectMocks
    private RoundEngine roundEngine;
    @Mock
    private User user1;
    @Mock
    private User user2;
    @Mock
    Deck deckA;
    @Mock
    Deck deckB;


    @BeforeEach
    void setUp() {
        roundEngine = new RoundEngine(user1, user2);
        when(user1.getDeck()).thenReturn(deckA);
        when(user2.getDeck()).thenReturn(deckB);
    }
    @Test
    @DisplayName("Bestimmt korrekt Gewinner und Verlierer basierend auf der Kartenstärke" +
            " und gibt ein gültiges Round-Objekt zurück.")
    void runTest() {
        Card cardA = new Ork("ork1", "Ork1", 100.0, Element.NORMAL);
        Card cardB = new Ork("ork2", "Ork2", 5.0, Element.NORMAL);

        when(deckA.pickRandomCard()).thenReturn(cardA);
        when(deckB.pickRandomCard()).thenReturn(cardB);
        Round round = roundEngine.run();
        assertNotNull(round);
        assertEquals(1.0, round.getEffectiveness());
        assertEquals(cardA, round.getWinnerCard());
        assertEquals(cardB, round.getLooserCard());
        assertFalse(round.isDraw());

        verify(deckA).addCard(cardB);
        verify(deckB).removeCard(cardB);
    }

    @Test
    @DisplayName("Unentschieden – keine Kartenbewegung")
    void runTest_Draw() {
        Card cardA = new Ork("ork1", "Ork1", 1.0, Element.NORMAL);
        Card cardB = new Ork("ork2", "Ork2", 1.0, Element.NORMAL);

        when(deckA.pickRandomCard()).thenReturn(cardA);
        when(deckB.pickRandomCard()).thenReturn(cardB);

        Round round = roundEngine.run();
        assertNotNull(round);
        assertEquals(1.0, round.getEffectiveness());
        assertTrue(round.isDraw());

        verify(deckA, never()).addCard(cardB);
        verify(deckB, never()).removeCard(cardB);
    }
}
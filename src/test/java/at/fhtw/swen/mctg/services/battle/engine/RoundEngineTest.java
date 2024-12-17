package at.fhtw.swen.mctg.services.battle.engine;

import at.fhtw.swen.mctg.core.cards.Element;
import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.monsters.Ork;
import at.fhtw.swen.mctg.core.cards.monsters.Wizard;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.Round;
import at.fhtw.swen.mctg.model.User;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;




import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RoundEngineTest {
    @Mock Deck mockedDeckA;
    @Mock Deck mockedDeckB;
    @Mock User userA;
    @Mock User userB;
    RoundEngine roundEngine;

    @BeforeEach
    void setUp() {
        userA = mock(User.class);
        userB = mock(User.class);
        roundEngine = new RoundEngine(userA, userB);
    }

    //28 combinations
    @Test
    @DisplayName("Wizard(10, NORMAL) vs Wizard(1.0, NORMAL) -> 1")
    void testRun() {
        Card monster1 = new Wizard("wizard1", "Wizard", 10.0, Element.NORMAL);
        Card monster2 = new Wizard("wizard2", "Wizard", 1.0, Element.NORMAL);

        when(mockedDeckA.getRandomCard()).thenReturn(monster1);
        when(mockedDeckB.getRandomCard()).thenReturn(monster2);

        Round round = roundEngine.run(mockedDeckA, mockedDeckB);
        assertEquals(userA, round.getWinner());
        assertEquals(monster2, round.getDefeatedCard());
        //что element не участвовали
    }
}
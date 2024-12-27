package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.core.cards.CardSet;
import java.util.Random;
import java.util.List;

public class Deck extends CardSet {
    public static final int EXACT_CARDS_REQUIRED = 4;
    private final Random random;

    private List<Card> cards;
    public Deck() {
        super(EXACT_CARDS_REQUIRED);
        random = new Random();
    }

    public Card getRandomCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        int randomIndex = random.nextInt(cards.size());
        return cards.get(randomIndex);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getDeck() {
        return cards;
    }
}

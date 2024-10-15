package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class CardSet {
    protected List<Card> cards;
    private final int maxSize;

    public CardSet(int maxSize) {
        this.cards = new ArrayList<>();
        this.maxSize = maxSize;
    }
    public CardSet(int maxSize, List<Card> cards) {
        if (cards.size() > maxSize) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " can contain max " + maxSize + " cards!");
        }
        this.cards = cards;
        this.maxSize = maxSize;
    }
    public void addCard(Card card) {
        if (cards.size() < maxSize) {
            cards.add(card);
        } else {
            throw new IllegalStateException("Max size of " + maxSize + " cards reached!");
        }
    }
    public boolean addCards(Card ...cards) {
        if (this.cards.size() + cards.length <= maxSize) {
            this.cards.addAll(List.of(cards));
            return true;
        }
        return false;
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public Card get(int i) {
        return cards.get(i);
    }
    public int getCount() {
        return cards.size();
    }

}

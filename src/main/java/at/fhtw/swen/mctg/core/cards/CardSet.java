package at.fhtw.swen.mctg.core.cards;

import at.fhtw.swen.mctg.model.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class CardSet {
    @Getter
    protected List<Card> cards;
    private int size;

    public CardSet() {
        cards = new ArrayList<>();
    }
    public CardSet(int size) {
        this.cards = new ArrayList<>();
        this.size = size;
    }
    public CardSet(int size, List<Card> cards) {
        if (cards.size() != size) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " must contain exactly " + size + " cards!");
        }
        this.cards = cards;
        this.size = size;
    }
    public CardSet(List<Card> cards) {
        this.cards = cards;
        this.size = cards.size();
    }
    public void addCard(Card card) {
        if (cards.size() < size) {
            cards.add(card);
        } else {
            throw new IllegalStateException("Max size of " + size + " cards reached!");
        }
    }
    public boolean addCards(List<Card> newCards) {
        if (this.cards.size() + newCards.size() <= size) {
            this.cards.addAll(newCards);
            return true;
        }
        return false;
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public boolean isEmpty() {
        System.out.println("---------isEmpty------------");
        return cards.isEmpty();
    }

    public Card get(int i) {
        return cards.get(i);
    }
    public int getCount() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }

}

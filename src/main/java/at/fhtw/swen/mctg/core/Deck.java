package at.fhtw.swen.mctg.core;

import java.util.ArrayList;
import java.util.List;

public class Deck implements CardSet{
    public static final int MAX_SIZE = 4;

    private List<Card> cards;
    public Deck() {
        cards = new ArrayList<>();
    }

    @Override
    public void addCard(Card card) {
        if (cards.size() < MAX_SIZE) {
            cards.add(card);
        }else {
            //TODO обработать
            throw new IllegalStateException("Deck can contain max 4 cards!");
        }
    }
    @Override
    public boolean addCards(Card ...cards) {
        if (this.cards.size() + cards.length  <= MAX_SIZE) {
            this.cards.addAll(List.of(cards));
            return true;
        }
        return false;
    }
    @Override
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Card get(int i) {
        return cards.get(i);
    }
}

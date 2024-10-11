package at.fhtw.swen.mctg.model;

public interface CardSet {
    void addCard(Card card);
    boolean removeCard(Card card);
    int getCount();
    boolean addCards(Card ...cards);
    Card get(int index);
}

package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.model.User;

public class Round {
    private User winner; //или карта
    private User looser; //или карта
    private Card playedCard;

    public User getWinner() {
        return winner;
    }

    ;

    public User getLooser() {
        return looser;
    }

    ;

    public void setWinner(User user) {
        winner = user;
    }

    public void setLooser(User user) {
        looser = user;
    }

    public Card getPlayedCard() {
        return this.playedCard;
    }
    public void setPlayedCard(Card card) {
        playedCard = card;
    }
}



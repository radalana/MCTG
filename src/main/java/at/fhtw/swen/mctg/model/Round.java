package at.fhtw.swen.mctg.model;

public class Round {
    private User winner;
    private User looser;
    private Card playedCard;

    public void start(Card card1, Card card2){};
    public User getWinner(){};
    public User getLooser(){};
    Card getPlayedCard(){
        return this.playedCard;
    }
}

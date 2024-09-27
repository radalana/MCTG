package at.fhtw.swen.mctg.core;

public class Round {
    private User winner; //или карта
    private User looser; //или карта
    private Card playedCard;

    public User getWinner(){
        return winner;
    };
    public User getLooser(){
        return looser;
    };

    public void setWinner(User user){
        winner = user;
    }

    public void setLooser(User user) {
        looser = user;
    }
    public void start(){
        
    }
    Card getPlayedCard(){
        return this.playedCard;
    }
}

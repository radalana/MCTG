package at.fhtw.swen.mctg.core;

import java.util.Collection;
import java.util.List;

public class Battle {
    static final int  MAX_ROUNDS = 100;
    private final User participant1;
    private final User participant2;
    List<String> log;
    List<Round> rounds;

    public Battle(User user1, User user2){
        participant1 = user1;
        participant2 = user2;
    }
    public void launch(){
        for(int i = 0; i < MAX_ROUNDS; i++) {
            Card card1 = chooseCard(User.getDeck());
            Card card2 = chooseCard(User.getDeck());
            Round round = new Round();
            round.start(card1, card2);
        }
    };
    private Card chooseCard(Collection<Card> userDeck) {
        //random
    };
}

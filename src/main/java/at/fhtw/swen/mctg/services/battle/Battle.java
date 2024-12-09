package at.fhtw.swen.mctg.services.battle;

import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.User;

public class Battle {
    private User player;
    private Deck deck;
    public Battle(Deck deck, User user) {
        player = user;
        this.deck = deck;
    }
    public void start() {

    }
}

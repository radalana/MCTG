package at.fhtw.swen.mctg.services;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private final String login;
    private String token;
    private String password;
    private Collection<Card> stack;
    private Deck deck; //TODO max 4 define es class maybe in Array List
    int stat;
    int coins = 20;
    //TODO deck in counstruction for test
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User(String login, String password, String token) {
        this.login = login;
        this.password = password;
        this.token = token;
    }
    public User(String login, Deck deck) {
        this.login = login;
        this.deck = deck; //TODO убрать deck после RoundServiceTest
    }
    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
    public Deck getDeck() {
        return deck;
    }

    private Deck chooseDeck() {return null;}

    /*
    TODO
    public void joinButtle() {
        Collection<Card> deck = this.chooseDeck();
    }

     */

    //Methods for trading
    /* TODO
    private Requirement choodeRequirments(){
        Requirement.CardType cardType;
        int minDamage;
        //scannrt
        //user writes 0 for spell, 1 for monster
        //user writes min damafe
        return new Requirement(cardType, minDamage);
    };
     */
    /*
    public void trade(Card card){
        Requirement claim = this.choodeRequirments();
        //TODO: chooseCard();
        Card cardForTrade = chooseCard()??
        TradeOffer offer = new TradeOffer(cardForTrade, claim);
        store.addItem(offer);
    }
     */
    //TODO public Package acquire(int coins){}
}

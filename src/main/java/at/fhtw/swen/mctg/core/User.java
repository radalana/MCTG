package at.fhtw.swen.mctg.core;

import at.fhtw.swen.mctg.services.trade.Requirement;
import at.fhtw.swen.mctg.services.trade.TradeOffer;

import java.util.Collection;
import java.util.List;

public class User {
    private String login;
    private String password;
    private Collection<Card> stack;
    private Deck deck; //TODO max 4 define es class maybe in Array List
    int stat;
    int coins = 20;
    //TODO deck in counstruction for test
    public User(String login, Deck deck) {
        this.login = login;
        this.deck = deck; //TODO убрать deck после RoundServiceTest
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

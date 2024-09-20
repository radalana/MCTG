package at.fhtw.swen.mctg.model;

import services.trade.Requirement;
import services.trade.TradeOffer;

import java.util.Collection;

public class User {
    private String login;
    private String password;
    private Collection<Card> stack;
    private Collection<Card> deck;
    int stat;
    int coins = 20;

    private Collection<Card> chooseDeck() {}

    public void joinButtle() {
        Collection<Card> deck = this.chooseDeck();
    }

    //Methods for trading
    private Requirement choodeRequirments(){
        Requirement.CardType cardType;
        int minDamage;
        //scannrt
        //user writes 0 for spell, 1 for monster
        //user writes min damafe
        return new Requirement(cardType, minDamage);
    };
    public void trade(Card card){
        Requirement claim = this.choodeRequirments();
        //TODO: chooseCard();
        Card cardForTrade = chooseCard()??
        TradeOffer offer = new TradeOffer(cardForTrade, claim);
        store.addItem(offer);
    }
    public Package acquire(int coins){}
}

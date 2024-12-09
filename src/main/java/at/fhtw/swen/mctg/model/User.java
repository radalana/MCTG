package at.fhtw.swen.mctg.model;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

public class User {
    @Getter
    private final String login;
    @Getter
    private String password;
    @Getter
    private String token;
    private Collection<Card> stack;
    @Getter
    private Deck deck; //TODO max 4 define es class maybe in Array List
    int stat;
    @Getter
    int coins = 20;
    //TODO deck in counstruction for test
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User(String login, Deck deck) {
        this.login = login;
        this.deck = deck; //TODO убрать deck после RoundServiceTest
    }
    public User(String login, String password, String token, int coins) {
        this.login = login;
        this.password = password;
        this.coins = coins;
        this.stack = stack;
    }

    public void addCardsToStack(List<Card> cards) {
        stack.addAll(cards);
    }
    public void spendFiveCoins() {
        if (coins < Package.PACKAGE_PRICE) {
            throw new IllegalStateException("Not enough coins to buy a package. Required: " + Package.PACKAGE_PRICE + ", Available: " + coins);
        }
        coins -= 5;
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

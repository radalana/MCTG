package at.fhtw.swen.mctg.model;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class User {
    private int id;
    private final String login;
    private String password;
    private Stack stack;
    int stat;
    int coins = 20;

    //TODO deck in counstruction for test
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User(int id, String login) {
        this.id = id;
        this.login = login;
        this.stack = new Stack();
    }

    // Constructor used in UserRepository's findUserByName method
    public User(int id, String login, String password, String token, int coins) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.coins = coins;
        this.stack = new Stack();
    }

    public void spendFiveCoins() {
        if (coins < Package.PACKAGE_PRICE) {
            throw new IllegalStateException("Not enough coins to buy a package. Required: " + Package.PACKAGE_PRICE + ", Available: " + coins);
        }
        coins -= 5;
    }

    public Deck getDeck() {
        return stack.getDeck();
    }
}

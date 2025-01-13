package at.fhtw.swen.mctg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class User {
    @JsonIgnore
    private int id;
    private final String username;
    private String password;
    @JsonIgnore
    private Stack stack;
    private int coins = 20;
    private String bio;
    private String image;


    //for get and edit user data
    public User(int id, String username, String password, String bio, String image, int coins) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
        this.coins = coins;
        this.stack = new Stack();
    }


    //TODO deck in counstruction for test
    public User(String login, String password) {
        this.username = login;
        this.password = password;
    }
    public User(int id, String login) {
        this.id = id;
        this.username = login;
        this.stack = new Stack();
    }

    // Constructor used in UserRepository's findUserByName method
    public User(int id, String username, String password, String token, int coins) {
        this.id = id;
        this.username = username;
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

    @JsonIgnore
    public Deck getDeck() {
        return stack.getDeck();
    }
}

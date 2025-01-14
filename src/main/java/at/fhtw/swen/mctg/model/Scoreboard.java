package at.fhtw.swen.mctg.model;

import lombok.Getter;

import java.util.Map;

//Lohnt es Singleton zu machen?
public class Scoreboard {
    private static Scoreboard scoreboard;
    @Getter
    private Map<String,Integer> usersElo;
    private Scoreboard(){};

    public static Scoreboard getInstance(Map<String,Integer> usersElo) {
        if (scoreboard == null) {
            scoreboard = new Scoreboard(usersElo);
        }
        return scoreboard;
    }
    public Scoreboard(Map<String,Integer> usersElo) {
        this.usersElo = usersElo;
    }
}

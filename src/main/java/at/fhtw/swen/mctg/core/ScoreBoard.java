package at.fhtw.swen.mctg.core;

//Lohnt es Singleton zu machen?
public class ScoreBoard {
    private static ScoreBoard scoreBoard;
    private ScoreBoard(){};

    public static ScoreBoard getInstance() {
        if (scoreBoard == null) {
            scoreBoard = new ScoreBoard();
        }
        return scoreBoard;
    }
}

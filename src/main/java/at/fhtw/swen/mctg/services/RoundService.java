package at.fhtw.swen.mctg.services;

import at.fhtw.swen.mctg.core.Card;
import at.fhtw.swen.mctg.core.Round;

public class RoundService {
    public static Round startRound(Card card1, Card card2) {
        Round round = new Round();//maybe better users, not cards;
          int comparisonResult =  Integer.compare(card1.getDamage(), card2.getDamage());
          switch (comparisonResult) {
              case 1:
                  round.setWinner(user1);
                  round.setLooser(user2);
                  break;
              case -1:
                  round.setWinner(user2);
                  round.setLooser(user1);
                  break;
              case 0:
                  round.setLooser(null);
                  round.setWinner(null);
                  break;
              default:
                  throw new IllegalArgumentException("Unexpected result " + comparisonResult);
          }
        return round;
    }
}

package at.fhtw.swen.mctg.services;

import at.fhtw.swen.mctg.core.Card;
import at.fhtw.swen.mctg.core.Round;
import at.fhtw.swen.mctg.core.User;

import java.util.Collection;


public class RoundService {
    private static Card chooseCardFromDeck(Collection<Card> deck){
        //TODO Card card = getRandomCard(Collection<Card> deck);
        return card;
    }
    private static int fight(Card card1, Card card2) {
        int comparisonResult =  Integer.compare(card1.getDamage(), card2.getDamage());
        return comparisonResult;
    }
    public static Round startRound(User user1, User user2) {
        var deck1 = user1.getDeck();
        var deck2 = user2.getDeck();
        Card card1 = chooseCardFromDeck(deck1);
        Card card2 = chooseCardFromDeck(deck2);
        Round round = new Round();//maybe better users, not cards;
        int comparisonResult = fight(card1, card2);
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

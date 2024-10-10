package at.fhtw.swen.mctg.core.services;

import at.fhtw.swen.mctg.core.Card;
import at.fhtw.swen.mctg.core.Deck;
import at.fhtw.swen.mctg.core.Round;
import at.fhtw.swen.mctg.core.User;

import java.util.List;


public class RoundService {
    private static Card chooseCardFromDeck(Deck deck){
        //TODO Card card = getRandomCard(Collection<Card> deck);
        //TODO выбрать реализацию для хранения карт: Arraylist/SortetList/LinkedList...??
         return deck.get(1);
    }

    static int fight(Card card1, Card card2) {
        if (card1.isMonsterType() && card2.isMonsterType()) {
            return Integer.compare(card1.getDamage(), card2.getDamage());
        }
        /*
        как хочу:
        int result = comparator.compare(card1, card2);


        if (sameElement) { //без учета элементов
            return Integer.compare(card1.getDamage(), card2.getDamage());
        }

        //с учетом элементов
        int dam1 = card1.getDamage()
        int dam2 = card2.getDamage()

        if (result > 0) {
            dam1 = dam1 * 2;
        }else {
            dam2 = dam2 * 2;
        }
        return Integer.compare(dam1, dam2);




        water vs fire
            if spell.type == water && card.type == fire {! случай когда вторая карта наоборот огонь и первая вода
                damage1 = spell.getDamage() * 2; // если наоборот то spell в два раз меньше
                damage2 = card.getDamage();
                return Integer.compare(damage1, damage2);
            }

            fire vs normal
            if (spell.fire vs card.normal) {
                damage1 = spell.getDamage() * 2; //и наоборот
                damage2 = card.getDamage();
            }

            normal vs water
            if (spell.type = normal && card.type == water) {
                damage1 = spell.getDamage() * 2;//и наоборот
                damage2 = card.getDamage();
                return Integer.compare(damage1, damage2);
            }
                что хочу и неважно порядок карт
                //TODO идея сравнение карт на основе элементов
                water -> fire
                fire -> normal
                normal -> water
                methodWaserAndFire(пара карт(карта1, карт2)){
                }
         */
        return 0;
    }
    public static Round startRound(User user1, User user2) {
        var deck1 = user1.getDeck();
        var deck2 = user2.getDeck();
        Card card1 = chooseCardFromDeck(deck1);
        Card card2 = chooseCardFromDeck(deck2);
        Round round = new Round();//maybe better users, not cards;
        int comparisonResult = fight(card1, card2);
        //TODO: implement how card transferred from looser to winner
          switch (comparisonResult) {
              case 1:
                  round.setWinner(user1);
                  round.setLooser(user2);
                  round.setPlayedCard(card2);
                  break;
              case -1:
                  round.setWinner(user2);
                  round.setLooser(user1);
                  round.setPlayedCard(card1);
                  break;
              case 0:
                  round.setLooser(null);
                  round.setWinner(null);
                  round.setPlayedCard(null);
                  break;
              default:
                  throw new IllegalArgumentException("Unexpected result " + comparisonResult);
          }
        return round;
    }
}

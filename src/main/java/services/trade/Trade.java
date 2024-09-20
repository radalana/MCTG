package services.trade;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
//in Service package
public class Trade {
    public static void trade(TradeOffer offer1, TradeOffer offer2) {
        Card card1 = offer1.getCard();
        Card card2 = offer2.getCard();
        boolean card1MeetsClaim = checkCardAgainstRequirements(offer1.getCard(), offer2.getRequirment();
        boolean card2MeetsClaim = checkCardAgainstRequirements(offer1.getCard(), offer2.getRequirment();
        User trader1 = offer1.getTrader();
        User trader2 = offer2.getTrader();
        if (card1MeetsClaim && card2MeetsClaim) {
            trader1.addCardToStack(card2);
            trader1.deleteCardFromStack(card1); //create class STack??? to implement add/delete
            trader2.addCardToStack(card1);
            trader2.deleteCardFromStack(card2);

            store.delete(card1, card2);
        }
    }
}

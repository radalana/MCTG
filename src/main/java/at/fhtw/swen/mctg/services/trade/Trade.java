package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

//in Service package
@Getter
@AllArgsConstructor
public class Trade {
    private int id;
    private TradeOffer offer;
    private User tradeRecipient;
    private Card cardToTrade;

    public Trade(TradeOffer offer, User tradeRecipient, Card cardToTrade) {
        this.offer = offer;
        this.tradeRecipient = tradeRecipient;
        this.cardToTrade = cardToTrade;
    }
}

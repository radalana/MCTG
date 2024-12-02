package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;

public class TradeOffer {
    private User trader;
    private Card card;
    private Requirement claim;

    public TradeOffer(User trader, Card card, Requirement claim) {
        this.trader = trader;
        this.card = card;
        this.claim = claim;
    }

    public User getTrader() {
        return this.trader;
    }

    public Card getCard() {
        return this.card;
    }
}

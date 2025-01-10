package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class TradeOffer {
    @NonNull
    private final String id;
    @NonNull
    private final Card card;
    @NonNull
    private final User trader;
    private final TradingController.RequiredType type;
    private final int minDamage;

    public TradeOffer(String id, Card card, User trader, TradingController.RequiredType type, int minDamage) {
        this.id = id;
        this.card = card;
        this.trader = trader;
        this.type = type;
        this.minDamage = minDamage;
    }
}

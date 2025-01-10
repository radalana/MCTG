package at.fhtw.swen.mctg.services.trade;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
public class TradeOffer {
    private String id;
    private String cardId;
    private int traderId;
    private TradingController.RequiredType type;
    private int minDamage;

    public TradeOffer(String id, String cardId, int traderId, TradingController.RequiredType type, int minDamage) {
        this.id = id;
        this.cardId = cardId;
        this.traderId = traderId;
        this.type = type;
        this.minDamage = minDamage;
    }
}

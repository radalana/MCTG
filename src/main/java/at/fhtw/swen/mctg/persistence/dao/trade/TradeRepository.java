package at.fhtw.swen.mctg.persistence.dao.trade;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.services.trade.Trade;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TradeRepository {
    private final UnitOfWork unitOfWork;
    public TradeRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void save(Trade trade) {
        String sql = "INSERT INTO trading_deals (offer_id, recipient_id, card_id) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            UUID offerId = UUID.fromString(trade.getOffer().getId());
            int recipientId = trade.getTradeRecipient().getId();
            UUID cardId = UUID.fromString(trade.getCardToTrade().getId());
            preparedStatement.setObject(1, offerId);
            preparedStatement.setInt(2, recipientId);
            preparedStatement.setObject(3, cardId);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during trading deal creation: " + e.getMessage(), e);
        }
    }
}

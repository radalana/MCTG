package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.services.trade.Trade;
import at.fhtw.swen.mctg.services.trade.TradeOffer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TradingRepository {
    private final UnitOfWork unitOfWork;
    public TradingRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void save(TradeOffer offer) {
        String sql = "INSERT INTO trading_offers (id, card_id, user_id, required_type, min_damage) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            UUID uuidOffer = UUID.fromString(offer.getId());
            UUID uuidCard = UUID.fromString(offer.getCardId());

            preparedStatement.setObject(1, uuidOffer);
            preparedStatement.setObject(2, uuidCard);
            preparedStatement.setInt(3, offer.getTraderId());
            preparedStatement.setString(4, offer.getType().toString());
            preparedStatement.setInt(5, offer.getMinDamage());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during trading offer creation: " + e.getMessage(), e);
        }
    }
}

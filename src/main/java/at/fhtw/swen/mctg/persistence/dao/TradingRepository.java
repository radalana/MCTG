package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.services.trade.Trade;
import at.fhtw.swen.mctg.services.trade.TradeOffer;
import at.fhtw.swen.mctg.services.trade.TradingController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            UUID uuidCard = UUID.fromString(offer.getCard().getId());

            preparedStatement.setObject(1, uuidOffer);
            preparedStatement.setObject(2, uuidCard);
            preparedStatement.setInt(3, offer.getTrader().getId());
            preparedStatement.setString(4, offer.getType().toString());
            preparedStatement.setInt(5, offer.getMinDamage());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during trading offer creation: " + e.getMessage(), e);
        }
    }

    public List<TradeOffer> findAll() {
        String sql = "SELECT * FROM trading_offers";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TradeOffer> offers = new ArrayList<>();
            while(resultSet.next()) {
                        String id = resultSet.getString(1);
                        String cardId = resultSet.getString(2);
                        int userId = resultSet.getInt(3);
                        TradingController.RequiredType requiredType = TradingController.RequiredType.fromString(resultSet.getString(4));
                        int minDamage = resultSet.getInt(5);

                        Card card = new CardDao(unitOfWork).findCardById(cardId);
                        User trader = new UserRepository(unitOfWork).findUserById(userId);

                        TradeOffer offer = new TradeOffer(id, card, trader, requiredType, minDamage);

                        offers.add(offer);
            }
            return offers;
        }catch (SQLException e) {
            throw new DataAccessException("Database SELECT operation failed: " + e.getMessage(), e);
        }
    }
}

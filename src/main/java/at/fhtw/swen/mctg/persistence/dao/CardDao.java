package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.core.cards.factories.CardFactory;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.dto.CardData;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardDao {
    private UnitOfWork unitOfWork;
    public CardDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void save(Card card, int packageId) {
        String query = """
                INSERT INTO cards (id, name, type, subtype, damage, element, package_id)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                """;
        String type = card instanceof Monster ? "monster" : "spell";
        String subType = card instanceof Monster ? ((Monster) card).getSubtype().toString() : null;
        UUID uuid = UUID.fromString(card.getId());
        var element = card.getElement() == null ? null : card.getElement().getValue();
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(query)) {
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, card.getName());
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, subType);
            preparedStatement.setDouble(5, card.getDamage());
            preparedStatement.setString(6, element);
            preparedStatement.setInt(7, packageId);
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            String duplicateKeySQLState = "23505";
            if (duplicateKeySQLState.equals(e.getSQLState())) {
                throw new DataAccessException("Failed to save card: card with id='" + uuid + "' already exists", e);
            }
            throw new DataAccessException("Failed to save card in data base: " + e.getMessage(), e);
        }
    }

    public void assignCardsToStack(int userStackId, int packageId) {
        String sql = "UPDATE cards SET stack_id = ? WHERE package_id = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userStackId);
            preparedStatement.setInt(2, packageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to assign cards to stack: " + e.getMessage(), e);
        }
    }

    public List<Card> getCardsByStackId(int stackId) {
        String sql = "SELECT * FROM cards WHERE stack_id = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, stackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<CardData> cardsData = new ArrayList<>();
            while (resultSet.next()) {
                CardData cardData = new CardData(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6)
                );
                cardsData.add(cardData);
            }
            CardFactory cardFactory = new CardFactory();
            return cardsData.stream()
                    .map(cardFactory::createCard)
                    .toList();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get cards by stack ID: " + e.getMessage(), e);
        }
    }
}

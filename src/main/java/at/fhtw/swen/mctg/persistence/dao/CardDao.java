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
    private final UnitOfWork unitOfWork;
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

    public void assignCardsToUser(int userId, int packageId) {
        String sql = "UPDATE cards SET user_id = ? WHERE package_id = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, packageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to assign cards to stack: " + e.getMessage(), e);
        }
    }

    //for GET/stack
    public List<Card> getCardsByStackId(int stackId) {
        String sql = "SELECT * FROM cards WHERE stack_id = ?";
        return getCards(sql, stackId);
    }

    //for GET/deck
    public List<Card> getCardsInDeckByStackId(int stackId) {
        String sql = "SELECT * FROM cards WHERE stack_id = ? AND is_in_deck = TRUE";
        return getCards(sql, stackId);
    }

    //TODO refactor extract duplicates
    public boolean areCardsWithIdExist(List<String> cardsIdList) {
        if (cardsIdList == null || cardsIdList.isEmpty()) {
            System.err.println("лол");
            return false;
        }
        String placeholders = String.join(",", cardsIdList.stream().map(id -> "?").toList());
        String sql = "SELECT COUNT(*) FROM cards WHERE id IN (" + placeholders + ")";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            for (int i = 0; i < cardsIdList.size(); i++) {
                preparedStatement.setObject(i + 1, UUID.fromString(cardsIdList.get(i)));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == cardsIdList.size();
            }else {
                System.err.println("что-то здесь");
                return false;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to check if cards with provided IDs exist. SQL " + e.getMessage(), e);
        }
    }

    public boolean areCardsInStackWithId(List<String> cardsIdList, int stackId) {
        System.err.println("Stack Id: " + stackId);
        //TODO вынести в DEck controller
        if (cardsIdList == null || cardsIdList.isEmpty()) {
            return false;
        }
        String placeholders = String.join(",", cardsIdList.stream().map(id -> "?").toList());
        String sql = "SELECT COUNT(*) FROM cards WHERE stack_id = ? AND id IN (" + placeholders + ")";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, stackId);
            for (int i = 0; i < cardsIdList.size(); i++) {
               // System.err.println("id: " + cardsIdList.get(i));
                preparedStatement.setObject(i+2, UUID.fromString(cardsIdList.get(i)));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.err.println(count);
                return count == cardsIdList.size();
            }
            return false;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to count cards in stack with stack_id = " + stackId
                    + ". SQL error: " + e.getMessage(), e);
        }
    }

    public int countCardsInDeck(int stackId) {
        String sql = "SELECT COUNT(*) FROM cards WHERE stack_id = ? AND is_in_deck = TRUE ";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, stackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DataAccessException("Failed count cards in deck");
            }
        }catch(SQLException e) {
            throw new DataAccessException("SELECT COUNT(*) failed count cards in deck", e);
        }
    }

    private List<Card> getCards(String sql, int stackId) {
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
            throw new DataAccessException("Failed to execute card query: " + e.getMessage(), e);
        }
    }

    public void setDeckFlagForCards(List<String> cardsIdList) {
        String placeholders = String.join(",", cardsIdList.stream().map(id -> "?").toList()); //или просто строка из ??
        String sql = "UPDATE cards SET is_in_deck = TRUE WHERE id IN (" + placeholders + ")";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            for (int i = 0; i < cardsIdList.size(); i++) {
                preparedStatement.setObject(i+1, UUID.fromString(cardsIdList.get(i)));
            }
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows < cardsIdList.size()) {
                throw new DataAccessException("Update incomplete. Expected to update " + cardsIdList.size() + " cards, but only " + updatedRows + " rows were modified.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update is_in_deck. SQL error: " + e.getMessage(), e);
        }
    }
}

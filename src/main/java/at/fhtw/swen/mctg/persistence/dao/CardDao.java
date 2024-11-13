package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.core.cards.Monster;
import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Package;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class CardDao {
    private UnitOfWork unitOfWork;
    public CardDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void save(Card card, int packageId) {
        System.out.println("CardDao:");
        System.out.println(card);
        String query = """
                INSERT INTO cards (id, name, type, subtype, damage, element, package_id)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                """;
        String subType = card instanceof Monster ? ((Monster) card).getSubtype().toString() : null;
        UUID uuid = UUID.fromString(card.getId());
        var element = card.getElement() == null ? null : card.getElement().getValue();
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(query)) {
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, card.getName());
            preparedStatement.setString(3, card instanceof Monster ? "monster" : "spell");
            preparedStatement.setString(4, subType);
            preparedStatement.setDouble(5, card.getDamage());
            preparedStatement.setString(6, element);
            preparedStatement.setInt(7, packageId);
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException("Failed to save card in data base: " + e.getMessage(), e);
        }
    };
}

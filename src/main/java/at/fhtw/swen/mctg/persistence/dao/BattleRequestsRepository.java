package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Deck;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BattleRequestsRepository {
    private final UnitOfWork unitOfWork;
    public BattleRequestsRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    //save request for a battle
    public void save(int userId) {
        String sql = "INSERT INTO battle_requests (user_id) VALUES (?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save battle request", e);
        }
    }
    //find opponent
    public User findUserOfEarliestRequest() {
        //TODO 2 mal 1 request zu verschiden users kann zuruck bla bla
        String sql ="""
        SELECT u.*
        FROM battle_requests br
        JOIN users u ON br.user_id = u.id
        ORDER BY br.created_at ASC
        LIMIT 1
    """;
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("findUserOfEarliestRequest " + e.getMessage(), e);
        }
    }
}

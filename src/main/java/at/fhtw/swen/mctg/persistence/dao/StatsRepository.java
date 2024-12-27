package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Stats;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsRepository {
    private final UnitOfWork unitOfWork;
    public StatsRepository (UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    public void create(int userId) {
        String sql = "INSERT INTO user_stats (user_id) VALUES (?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT failed during stats creation: " + e.getMessage(), e);
        }
    }

    public Stats findStats(int userId) {
        String sql = "SELECT * FROM user_stats WHERE user_id = ?";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Stats(
                        userId,
                        resultSet.getInt("wins"),
                        resultSet.getInt("losses"),
                        resultSet.getInt("draws"),
                        resultSet.getInt("total_battles"),
                        resultSet.getInt("elo")
                );
            } else {
                throw new DataAccessException("Stats for user: " + userId + " is not found");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database SELECT failed while finding states for user_id " + userId + ". " + e.getMessage(), e);
        }

    }
}

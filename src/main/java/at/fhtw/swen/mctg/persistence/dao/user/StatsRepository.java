package at.fhtw.swen.mctg.persistence.dao.user;

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

    public void save(Stats stats) {
        String sql = "UPDATE user_stats SET wins = ?, losses = ?, draws = ?, total_battles = ?, elo = ? WHERE user_id = ?";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, stats.getWins());
            preparedStatement.setInt(2, stats.getLosses());
            preparedStatement.setInt(3, stats.getDraws());
            preparedStatement.setInt(4, stats.getTotal_battles());
            preparedStatement.setInt(5, stats.getElo());
            preparedStatement.setInt(6, stats.getUser_id());
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException("Database UPDATE failed during saving stats for user_id: " + stats.getUser_id() + ". " + e.getMessage(), e);
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

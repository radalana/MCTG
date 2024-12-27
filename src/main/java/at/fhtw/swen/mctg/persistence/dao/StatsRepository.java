package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
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
            throw new DataAccessException("Database INSERT operation failed during stats creation: " + e.getMessage(), e);
        }
    }
}

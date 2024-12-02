package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class StackRepository {
    private final UnitOfWork unitOfWork;
    public StackRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public int create(int userId) {
        String sql = "INSERT INTO stacks (user_id) VALUES (?)";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql, true)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            }else {
                throw new DataAccessException("Could not create stack for user " + userId);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during stack creation: " + e.getMessage());
        }
    }

}

package at.fhtw.swen.mctg.persistence.dao.cards;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int findStackByUsername(String username) {
        String sql = """
                        SELECT stacks.id AS stack_id
                        FROM stacks
                        JOIN users ON stacks.user_id = users.id
                        WHERE users.username = ?
                        """;
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DataAccessException("No stack found for the user with username: " + username);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database error occurred while fetching stack for username: " + username, e);
        }
    }
}

package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.services.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class UserRepository {
    private final UnitOfWork unitOfWork; //не уверена что нужно создавать здесь

    public UserRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("token")
                );
            }
            return null;
        }catch (SQLException e) {
            throw  new DataAccessException(e);
        }
    }
    public void save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            //System.err.println("SQLException in save(): " + e.getMessage());
            throw new DataAccessException("Error in save()", e);
        }
    }
}

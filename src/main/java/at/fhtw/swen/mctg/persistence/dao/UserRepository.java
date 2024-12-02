package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
                        resultSet.getString("token"),
                        resultSet.getInt("coins")
                );
            }
            return null;
        }catch (SQLException e) {
            throw  new DataAccessException(e);
        }
    }
    public User findUserByToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        String sql = "SELECT * FROM users WHERE token = ?";
        System.out.println("Token in findUserByToken: " + token);
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("token"),
                        resultSet.getInt("coins")
                );
            } else {
                //TODO change to optional?
                throw new DataAccessException("User not found");
            }
        }catch (SQLException e) {
            throw  new DataAccessException(e);
        }
    }
    public int save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql, true)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new DataAccessException("Could not save user");
            }
        }catch (SQLException e) {
            //System.err.println("SQLException in save(): " + e.getMessage());
            throw new DataAccessException("Database INSERT operation failed during package creation: " + e.getMessage());
        }
    }

    public void updateToken(String username, String newToken) {
        String sql = "UPDATE users SET token = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, newToken);
            preparedStatement.setString(2, username);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("Failed to update token: User with username '" + username + "' not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database UPDATE operation failed: " + e.getMessage(), e);
        }
    }

    //beim Packagekauf
    public void updateCoins(User user) {
        String sql = "UPDATE users SET coins = ? WHERE username = ?";
        String username = user.getLogin();
        int coins = user.getCoins();
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, coins);
            preparedStatement.setString(2, username);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("Failed to update coins: User with username '" + username + "' not found.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Failed to update coins for user '" + username + "': " + e.getMessage(), e);
        }
    }
}

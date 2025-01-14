package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Scoreboard;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardRepository {
    private final UnitOfWork unitOfWork;
    public ScoreboardRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public Scoreboard find() {
        String sql = "SELECT * FROM scoreboard";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String,Integer> userAndElo = new HashMap<>();
            while (resultSet.next()) {
                userAndElo.put(resultSet.getString("username"), resultSet.getInt("elo"));
            }
            return Scoreboard.getInstance(userAndElo);
        }catch (SQLException e) {
            throw new DataAccessException("Failed to fetch scoreboard from database: " + e.getMessage(), e);
        }
    }
}

package at.fhtw.swen.mctg.persistence;

import at.fhtw.swen.mctg.model.BattleResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleResultRepository {
    private final UnitOfWork unitOfWork;
    public BattleResultRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    public int save(BattleResult battleResult) {
        String sql = "INSERT INTO battles_results (user_id, result) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql, true)) {
            preparedStatement.setInt(1, battleResult.getUserId());
            preparedStatement.setInt(2, battleResult.getResult());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }else {
                throw new DataAccessException("Could not save battle result");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during battle_result creation: " + e.getMessage(), e);
        }
    }
}

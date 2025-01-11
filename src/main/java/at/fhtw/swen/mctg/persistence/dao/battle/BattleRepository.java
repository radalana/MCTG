package at.fhtw.swen.mctg.persistence.dao.battle;

import at.fhtw.swen.mctg.model.Battle;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BattleRepository {
    private final UnitOfWork unitOfWork;
    public BattleRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    public void save(Battle battle, int user1ResultId, int user2ResultId) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String sql = "INSERT INTO battles (rounds, number_of_rounds, user1_result_id, user2_result_id) VALUES (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            String roundsJSON = objectMapper.writeValueAsString(battle.getRounds());
            preparedStatement.setObject(1, roundsJSON, java.sql.Types.OTHER);
            preparedStatement.setInt(2, battle.getNumberOfRounds());
            preparedStatement.setInt(3, user1ResultId);
            preparedStatement.setInt(4, user2ResultId);
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during battle creation: " + e.getMessage(), e);
        }
    }
}

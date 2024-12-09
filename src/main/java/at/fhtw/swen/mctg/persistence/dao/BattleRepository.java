package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Battle;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

public class BattleRepository {
    private final UnitOfWork unitOfWork;
    public BattleRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    public void save(Battle battle) {

    }
}

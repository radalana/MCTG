package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.model.Package;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PackageDao {
    private final UnitOfWork unitOfWork;
    public PackageDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    public int save(Package cardPackage) {
        String sqlQueryPackages = "INSERT INTO packages DEFAULT VALUES";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sqlQueryPackages, true)){
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new DataAccessException("No ID generated for the inserted package.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database INSERT operation failed during package creation: " + e.getMessage());
        }
    }

}

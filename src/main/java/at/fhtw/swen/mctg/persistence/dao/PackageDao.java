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

    // Wird verwendet, wenn ein Admin ein neues Paket erstellt.
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

    // Wird verwendet, um die ID des ältesten Pakets anhand des Erstellungsdatums auszuwählen.
    public int getFirstCreatedPackageId() {
        String sql = "SELECT * FROM packages ORDER BY created_at ASC LIMIT 1";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new DataAccessException("No packages available.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch a oldest package ID: " + e.getMessage(), e);
        }
    }

    public void delete(int packageId) {
        String sqlQuery = "DELETE FROM packages WHERE id = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, packageId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DataAccessException("No package found with ID: " + packageId);//не должно быть в ответе
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete package with ID: " + packageId, e);
        }
    }

}

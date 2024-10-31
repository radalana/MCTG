package at.fhtw.swen.mctg.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    //чтобы не создавать дополнительный объект, как альтернатива static
    INSTANCE;

    public Connection getConnection() {
            try {
                return DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mctg",
                        "sveta",
                        "sveta1234");
            } catch (SQLException e) {
                throw new DataAccessException("Datenbankverbindungsaufbau nicht erfolgreich", e);
            }
    }
}


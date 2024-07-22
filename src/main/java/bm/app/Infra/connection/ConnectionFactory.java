package bm.app.Infra.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/atendimentos-app", "postgres", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
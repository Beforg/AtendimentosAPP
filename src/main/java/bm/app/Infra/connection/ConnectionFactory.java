package bm.app.Infra.connection;

import bm.app.Infra.configuration.ConfigUtil;
import bm.app.Utils.CaixaDeMensagem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final String URL = "jdbc:" + ConfigUtil.getProperty("urlBanco");
    private final String USER = ConfigUtil.getProperty("user");
    private final String PASSWORD = ConfigUtil.getProperty("senha");
    public ConnectionFactory() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro na conexão com o banco de dados", "Erro na conexão com o banco de dados, por favor verifique as configurações. " , "botao-x.png");
            throw new RuntimeException(e);
        }
    }
}
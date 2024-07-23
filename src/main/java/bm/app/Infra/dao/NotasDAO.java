package bm.app.Infra.dao;

import bm.app.Infra.connection.ConnectionFactory;
import bm.app.Model.notas.Notas;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotasDAO {
    ConnectionFactory connectionFactory;

    public NotasDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void criarNota(Notas nota) {
        String sql = "INSERT INTO anotacoes (titulo, descricao, data_criacao) VALUES (?, ?, ?)";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nota.getTitulo());
            ps.setString(2, nota.getDescricao());
            ps.setObject(3, nota.getDataCriacao());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void editarNota(Notas nota) {
        String sql = "UPDATE anotacoes SET titulo = ?, descricao = ?, data_edicao = ? WHERE id = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nota.getTitulo());
            ps.setString(2, nota.getDescricao());
            ps.setObject(3, nota.getDataEdicao());
            ps.setInt(4, nota.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void removerNota(Notas nota) {
        String sql = "DELETE FROM anotacoes WHERE id = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nota.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void listarNotasDia(ListView<Notas> listView, LocalDate localDate) {
        String sql = "SELECT * FROM anotacoes WHERE data_criacao = ?";
        Connection conn = connectionFactory.getConnection();
        List<Notas> notas;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, localDate);
            ResultSet resultSet = ps.executeQuery();
            getNotasToList(conn, resultSet, listView);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public void listarNotasHoje(ListView<Notas> listView) {
        String sql = "SELECT * FROM anotacoes WHERE data_criacao = CURRENT_DATE";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            getNotasToList(conn, resultSet, listView);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    private void getNotasToList(Connection conn, ResultSet resultSet, ListView<Notas> notasListView) throws SQLException {
        List<Notas> notasTemp = new ArrayList<>();
        while (resultSet.next()) {
            Notas nota = new Notas();
            nota.setId(resultSet.getInt("id"));
            nota.setTitulo(resultSet.getString("titulo"));
            nota.setDescricao(resultSet.getString("descricao"));
            nota.setDataCriacao(resultSet.getDate("data_criacao").toLocalDate());
            if (resultSet.getDate("data_edicao") != null) {
                nota.setDataEdicao(resultSet.getDate("data_edicao").toLocalDate());
            } else {
                nota.setDataEdicao(null);
            }
            notasTemp.add(nota);
        }
        Platform.runLater(() -> notasListView.getItems().addAll(notasTemp));
        conn.close();
    }
}

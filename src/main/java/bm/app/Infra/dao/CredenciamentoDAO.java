package bm.app.Infra.dao;

import bm.app.Infra.connection.ConnectionFactory;
import bm.app.Infra.security.Criptografia;
import bm.app.Model.credenciamento.AdminTableView;
import bm.app.Model.credenciamento.Cargo;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Model.credenciamento.FuncionariosTableView;
import bm.app.Utils.CaixaDeMensagem;
import javafx.collections.ObservableList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredenciamentoDAO {
    private ConnectionFactory connectionFactory;

    public CredenciamentoDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void criar(Credenciamento credenciamento) {
        String sql = "INSERT INTO contas (nome, usuario, senha, tipo) VALUES (?, ?, ?, ?)";
        Connection conn = connectionFactory.getConnection();
        try {
            String senhaCriptografada = Criptografia.criptografa(credenciamento.getPassword());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, credenciamento.getNome());
            ps.setString(2, credenciamento.getUsername());
            ps.setString(3, senhaCriptografada);
            ps.setString(4, String.valueOf(credenciamento.getCargo()));
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public boolean verificaLogin(String senha, String username) {
        String sql = "SELECT senha FROM contas WHERE usuario = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String senhaCriptografada = rs.getString("senha");
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                conn.close();
                return encoder.matches(senha, senhaCriptografada);
            } else {
                CaixaDeMensagem.mensagemErro("Erro", "Usuário não encontrado", "Usuário não encontrado no banco de dados","botao-x.png");
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public void excluir(Credenciamento credenciamento) {
        String sql = "DELETE FROM contas WHERE id = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, credenciamento.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public void listarFuncionario(ObservableList<FuncionariosTableView> list) {
        String sql = "SELECT * FROM contas WHERE tipo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(Cargo.FUNCIONARIO));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Credenciamento credenciamento = criaCredenciamento(rs);
                FuncionariosTableView funcionariosTableView = new FuncionariosTableView(credenciamento);

                list.add(funcionariosTableView);

            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public  void listarAdmin(ObservableList<AdminTableView> list) {
        String sql = "SELECT * FROM contas WHERE tipo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(Cargo.ADMINISTRADOR));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Credenciamento credenciamento = criaCredenciamento(rs);
                AdminTableView adminTableView = new AdminTableView(credenciamento);

                list.add(adminTableView);

            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public Credenciamento carregarCredenciamento(String username) {
        String sql = "SELECT * FROM contas WHERE usuario = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Credenciamento credenciamento = criaCredenciamento(rs);
                conn.close();
                return credenciamento;
            } else {
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    private Credenciamento criaCredenciamento(ResultSet rs) throws SQLException {
        Credenciamento credenciamento = new Credenciamento();
        credenciamento.setId(rs.getInt("id"));
        credenciamento.setNome(rs.getString("nome"));
        credenciamento.setUsername(rs.getString("usuario"));
        credenciamento.setPassword(rs.getString("senha"));
        credenciamento.setCargo(Cargo.valueOf(rs.getString("tipo")));
        return credenciamento;
    }

    public boolean verificaUsernameExistente(Credenciamento credenciamento) {
        String sql = "SELECT * FROM contas WHERE usuario = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, credenciamento.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

}

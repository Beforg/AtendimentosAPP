package bm.app.Infra.dao;

import bm.app.Infra.connection.ConnectionFactory;
import bm.app.Model.cliente.Cliente;
import bm.app.Model.cliente.ClientesTableView;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private final ConnectionFactory connectionFactory;

    public ClienteDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, telefone, endereco) VALUES (?,?,?)";
        Connection con = connectionFactory.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEndereco());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void removerCliente(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        Connection con = connectionFactory.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void editarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, endereco = ? WHERE id = ?";
        Connection con = connectionFactory.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEndereco());
            ps.setInt(4, cliente.getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void listarClientes(ObservableList<ClientesTableView> clientesTableViewsList) {
        String sql = "SELECT * FROM clientes";
        Connection con = connectionFactory.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setEndereco(resultSet.getString("endereco"));
                clientesTableViewsList.add(new ClientesTableView(cliente));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void listarClientesComboBox(ComboBox<Cliente> cbSelecionarCliente) {
        String sql = "SELECT * FROM clientes";
        Connection con = connectionFactory.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cbSelecionarCliente.getItems().add(cliente);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}

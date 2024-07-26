package bm.app.Infra.dao;

import bm.app.Infra.connection.ConnectionFactory;
import bm.app.Model.anotacoes.Notas;
import bm.app.Model.cliente.Cliente;
import bm.app.Model.notas.NotasCliente;
import bm.app.Model.notas.NotasClienteTableView;
import bm.app.Model.pedidos.Pedido;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotasClienteDAO {
    private final ConnectionFactory connectionFactor;

    public NotasClienteDAO() {
        this.connectionFactor = new ConnectionFactory();
    }

    public void registrarNotaCliente(List<NotasCliente> notaCliente) {
        String sql = "INSERT INTO notas_pendentes (data,valor,chave_peso,cliente_id, codigo_pedido) VALUES (?, ?, ?, ?, ?)";
        Connection conn = connectionFactor.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (NotasCliente nota : notaCliente) {
                ps.setDate(1, Date.valueOf(nota.getDataPedido()));
                ps.setBigDecimal(2, nota.getValorPedido());
                ps.setDouble(3, Double.parseDouble(nota.getChavePeso()));
                ps.setInt(4, Integer.parseInt(nota.getIdCliente()));
                ps.setObject(5, UUID.fromString(nota.getIdPedido()));
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public boolean verificaCodigoPedidoJaRegistrado(UUID codigo) {
        String sql = "SELECT * FROM notas_pendentes WHERE codigo_pedido = ?";
        Connection conn = connectionFactor.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, codigo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void carregarParaTabela(ObservableList<NotasClienteTableView> list) {
        String sql = "SELECT notas_pendentes.*, clientes.nome AS cliente_nome\n" +
                "FROM notas_pendentes\n" +
                "JOIN clientes ON clientes.id = notas_pendentes.cliente_id";
        Connection conn = connectionFactor.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NotasCliente nota = new NotasCliente();
                nota.setIdCliente(String.valueOf(rs.getInt("cliente_id")));
                nota.setDataPedido(String.valueOf(rs.getDate("data").toLocalDate()));
                nota.setValorPedido(rs.getBigDecimal("valor"));
                nota.setChavePeso(rs.getString("chave_peso"));
                nota.setNomeCliente(rs.getString("cliente_nome"));
                nota.setIdPedido(String.valueOf(rs.getObject("codigo_pedido")));
                NotasClienteTableView tableView = new NotasClienteTableView(nota);
                list.add(tableView);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public List<Pedido> carregaPedidosDoDia() {
        String sql = "SELECT pedidos.*, clientes.nome AS cliente_nome\n" +
                "FROM pedidos \n" +
                "JOIN clientes ON clientes.id = pedidos.clientes_id\n" +
                "WHERE pedidos.data_pedido = CURRENT_DATE AND pedidos.pago = FALSE AND pedidos.entregue = TRUE;";
//        String sql = "SELECT notas_pendentes.*, clientes.nome AS cliente_nome\n" +
//                "FROM notas_pendentes\n" +
//                "JOIN clientes ON clientes.id = notas_pendentes.cliente_id\n" +
//                "JOIN pedidos ON pedidos.codigo = notas_pendentes.codigo_pedido\n" +
//                "WHERE pedidos.entregue = TRUE AND pedidos.pago = FALSE";
        Connection conn = connectionFactor.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId((UUID) rs.getObject("codigo"));
                pedido.setDataPedido(rs.getDate("data_pedido").toLocalDate());
                pedido.setValor(rs.getBigDecimal("valor"));
                pedido.setChavePeso(String.valueOf(rs.getDouble("chave_peso")));
                pedido.setStatusPedido(rs.getString("status"));
                pedido.setPago(rs.getBoolean("pago"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("clientes_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                pedido.setCliente(cliente);
                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public void removerNota(NotasCliente nota) {
        String sql = "DELETE FROM notas_pendentes WHERE codigo_pedido = ?";
        Connection conn = connectionFactor.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(nota.getIdPedido()));
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}

package bm.app.Infra.dao;

import bm.app.Infra.connection.ConnectionFactory;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.UUID;

public class PedidoDAO {
    ConnectionFactory connectionFactory;

    public PedidoDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void criarPedido(Pedido pedido) {
       String sql = "INSERT INTO pedidos (codigo, nome, status, valor, forma_pagamento, entregador, pago, entregue," +
               " chave_peso, clientes_id, data_pedido, hora_pedido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
       Connection conn = connectionFactory.getConnection();

       try {
           PreparedStatement ps = conn.prepareStatement(sql);
           double chavePesoValue = Double.parseDouble(pedido.getChavePeso().replace(",", "."));
           ps.setObject(1, pedido.getId());
           ps.setString(2, pedido.getNome());
           ps.setString(3, pedido.getStatusPedido());
           ps.setBigDecimal(4, pedido.getValor());
           ps.setString(5, pedido.getFormaPagamento());
           ps.setString(6, pedido.getEntregador());
           ps.setBoolean(7, pedido.verificaPago());
           ps.setBoolean(8, pedido.verificaEntregue());
           ps.setDouble(9, chavePesoValue);
           if (pedido.getCliente() != null) {
               ps.setInt(10, pedido.getCliente().getId());
           } else {
               ps.setNull(10, java.sql.Types.INTEGER);
           }
           ps.setDate(11, Date.valueOf(pedido.getDataPedido()));
           ps.setTime(12, Time.valueOf(pedido.getHoraPedido()));
           ps.execute();
           conn.close();

       } catch (SQLException e) {
           throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
       }
    }

    public void carregarPedidoHoje(ObservableList<PedidoTableView> lista) {
        String sql = "SELECT * FROM pedidos WHERE data_pedido = CURRENT_DATE";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId((UUID) resultSet.getObject("codigo"));
                pedido.setNome(resultSet.getString("nome"));
                pedido.setStatusPedido(resultSet.getString("status"));
                pedido.setValor(resultSet.getBigDecimal("valor"));
                pedido.setFormaPagamento(resultSet.getString("forma_pagamento"));
                pedido.setEntregador(resultSet.getString("entregador"));
                pedido.setPago(resultSet.getBoolean("pago"));
                pedido.setEntregue(resultSet.getBoolean("entregue"));
                pedido.setChavePeso(String.valueOf(resultSet.getDouble("chave_peso")));
                pedido.setDataPedido(resultSet.getDate("data_pedido").toLocalDate());
                pedido.setHoraPedido(resultSet.getTime("hora_pedido").toLocalTime());

                PedidoTableView pedidoTableView = new PedidoTableView(pedido);
                lista.add(pedidoTableView);

            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void atualizarNome(Pedido pedido) {
        String sql = "UPDATE pedidos SET nome = ? WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pedido.getNome());
            ps.setObject(2, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
    public void atualizarEntregador(Pedido pedido) {
        String sql = "UPDATE pedidos SET entregador = ? WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pedido.getEntregador());
            ps.setObject(2, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void atualizarStatus(Pedido pedido) {
        String sql = "UPDATE pedidos SET status = ? WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pedido.getStatusPedido());
            ps.setObject(2, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }


    public void atualizarAndamento(Pedido pedido) {
        String sql = "UPDATE pedidos SET entregue = ?, pago = ?, status = ?  WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, pedido.verificaPago());
            ps.setObject(2, pedido.verificaEntregue());
            ps.setString(3, pedido.getStatusPedido());
            ps.setObject(4, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void removerPedido(Pedido pedido) {
        String sql = "DELETE FROM pedidos WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void atualizarValorPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET valor = ?, status = ? WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, pedido.getValor());
            ps.setString(2, pedido.getStatusPedido());
            ps.setObject(3, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public void atualizarFormaPagamaneto(Pedido pedido) {
        String sql = "UPDATE pedidos SET forma_pagamento = ? WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pedido.getFormaPagamento());
            ps.setObject(2, pedido.getId());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }

    public Pedido buscarPedidoPorId(UUID id) {
        String sql = "SELECT * FROM pedidos WHERE codigo = ?";
        Connection conn = connectionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            Pedido pedido = new Pedido();
            while (resultSet.next()) {
                pedido.setId((UUID) resultSet.getObject("codigo"));
                pedido.setNome(resultSet.getString("nome"));
                pedido.setStatusPedido(resultSet.getString("status"));
                pedido.setValor(resultSet.getBigDecimal("valor"));
                pedido.setFormaPagamento(resultSet.getString("forma_pagamento"));
                pedido.setEntregador(resultSet.getString("entregador"));
                pedido.setPago(resultSet.getBoolean("pago"));
                pedido.setEntregue(resultSet.getBoolean("entregue"));
                pedido.setChavePeso(String.valueOf(resultSet.getDouble("chave_peso")));
                pedido.setDataPedido(resultSet.getDate("data_pedido").toLocalDate());
                pedido.setHoraPedido(resultSet.getTime("hora_pedido").toLocalTime());
            }
            conn.close();
            return pedido;
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}

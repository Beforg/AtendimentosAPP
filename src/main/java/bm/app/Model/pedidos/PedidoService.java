package bm.app.Model.pedidos;

import bm.app.Infra.dao.NotasClienteDAO;
import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.FormaPagamento;
import bm.app.Model.StatusPedido;
import bm.app.Model.cliente.Cliente;
import bm.app.Model.notas.NotasCliente;
import bm.app.Model.notas.NotasClienteService;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.UUID;


public class PedidoService {
    public Pedido criarPedido(String nome, BigDecimal valor, FormaPagamento formaPagamento, String entregador,
                              StatusPedido statusPedido, String chavePeso, boolean pedidoFeito, Cliente cliente, ComboBox<Cliente> cb) {
        UUID id = UUID.randomUUID();
        String nomeEscolhido;
        if (cb.getSelectionModel().getSelectedItem() == null && nome.isEmpty()) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao adicionar pedido", "Dados inválidos.","botao-x.png");
            throw new IllegalArgumentException("Erro ao criar o pedido: Sem dados válidos.");
        }
        if (nome.isEmpty()) {
            nomeEscolhido = cb.getSelectionModel().getSelectedItem().getNome();
        } else {
            nomeEscolhido = nome;
        }

        Pedido pedido = new Pedido(id, nomeEscolhido, valor, formaPagamento.getFormaPagamento(),
                entregador, statusPedido.getStatusPedido(), chavePeso, cliente);
        try {
            boolean valido = Validacao.validarCamposAdicionarPedido(pedido, pedidoFeito);
            if (valido) {
                return pedido;
            }
        } catch (IllegalArgumentException e) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao adicionar pedido", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
        }
        return null;
    }

    public void atualizarPedido(CheckBox pago, CheckBox entregue, TableView<PedidoTableView> tabela, ObservableList<PedidoTableView> lista,
                                PedidoDAO pedidoDAO) {
        PedidoTableView pedido = tabela.getSelectionModel().getSelectedItem();
        if (pago.isSelected() && entregue.isSelected()) {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.ENTREGUE, true, true);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else if (pago.isSelected() && !entregue.isSelected())   {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.PAGO, true, false);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else if (entregue.isSelected() && !pago.isSelected()) {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.NAO_PAGO, false, true);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.PENDENTE, false, false);
            pedidoDAO.atualizarAndamento(pedidoDb);
        }
        tabela.refresh();
        AppUtils.atualizarTabelas(lista);
    }

    public void atualizarPedidoBuscador(CheckBox pago, CheckBox entregue, Pedido pedido, PedidoDAO pedidoDAO,
                                        NotasClienteDAO notasClienteDAO,
                                        NotasClienteService notasClienteService) {

        Cliente cliente = new Cliente();
        cliente.setId(0);
        cliente.setNome("");

        if (pago.isSelected() && entregue.isSelected()) {

            pedido.setPago(true);
            pedido.setEntregue(true);
            pedido.setStatusPedido(StatusPedido.ENTREGUE.getStatusPedido());
            pedidoDAO.atualizarAndamento(pedido);
            pedido.setCliente(cliente);
            NotasCliente notasCliente = new NotasCliente(pedido);
            notasClienteService.removerNotaCliente(notasCliente, notasClienteDAO);

        } else if (pago.isSelected() && !entregue.isSelected())   {
            pedido.setPago(true);
            pedido.setEntregue(false);
            pedido.setStatusPedido(StatusPedido.PAGO.getStatusPedido());
            pedidoDAO.atualizarAndamento(pedido);
            pedido.setCliente(cliente);
            NotasCliente notasCliente = new NotasCliente(pedido);
            notasClienteService.removerNotaCliente(notasCliente, notasClienteDAO);

        } else if (entregue.isSelected() && !pago.isSelected()) {
            pedido.setPago(false);
            pedido.setEntregue(true);
            pedido.setStatusPedido(StatusPedido.NAO_PAGO.getStatusPedido());
            pedidoDAO.atualizarAndamento(pedido);
        } else {
            pedido.setPago(false);
            pedido.setEntregue(false);
            pedido.setStatusPedido(StatusPedido.PENDENTE.getStatusPedido());
            pedidoDAO.atualizarAndamento(pedido);
        }
    }

    private Pedido settarPedido(PedidoTableView pedido, StatusPedido statusPedido, boolean pago, boolean entregue) {
        pedido.setStatus(statusPedido.getStatusPedido());
        pedido.setPago(pago);
        pedido.setEntregue(entregue);
        return new Pedido(pedido);
    }
    public void removerPedido(ObservableList<PedidoTableView> list, PedidoDAO pedidoDAO){
        ObservableList<PedidoTableView> removePedidoTableView = FXCollections.observableArrayList();

        for (PedidoTableView pedidoTableView : list){
            if (pedidoTableView.getRemover().isSelected())
            {
                removePedidoTableView.add(pedidoTableView);
            }
        }
        if (removePedidoTableView.isEmpty()) {
            Alert removerVazio = new Alert(Alert.AlertType.ERROR);
            removerVazio.setTitle("Erro");
            removerVazio.setHeaderText("Nenhum atendimento selecionado");
            Stage alertStage = (Stage) removerVazio.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("botao-x.png"));
            removerVazio.showAndWait();

        } else {
            Alert confirmaRemover = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaRemover.setTitle("Remover atendimento");
            confirmaRemover.setHeaderText("Confirmar");
            Stage alertStage = (Stage) confirmaRemover.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("aviso.png"));
            confirmaRemover.setContentText("Tem certeza que deseja remover os atendimentos selecionados? os atendimentos excluídos não poderão ser recuperados.");

            confirmaRemover.getButtonTypes().stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .ifPresent(buttonType -> {
                        Button button = (Button) confirmaRemover.getDialogPane().lookupButton(buttonType);
                        button.setDefaultButton(false);

                    });

            if (confirmaRemover.showAndWait().get() == ButtonType.OK) {
                for (PedidoTableView pedidoTableView : removePedidoTableView) {
                    try {
                        Pedido pedido = new Pedido(pedidoTableView);
                        pedidoDAO.removerPedido(pedido);
                    } catch (RuntimeException e) {
                        CaixaDeMensagem.mensagemErro("Erro", "Erro ", "Erro ao remover atendimento.","botao-x.png");
                        throw new RuntimeException("Erro ao remover atendimento.");
                    }

                }
                list.removeAll(removePedidoTableView);

            }
        }
    }
    public Pedido buscarPorId(PedidoDAO pedidoDAO,String codigo, Label nome, Label valor, Label data, Label peso, CheckBox pago,
                              CheckBox entregue) {
        try {
            Pedido pedido = pedidoDAO.buscarPedidoPorId(UUID.fromString(codigo));
            if (pedido != null) {
                nome.setText(pedido.getNome());
                valor.setText("R$ " + pedido.getValor().toString());
                data.setText(pedido.getDataPedido().toString());
                peso.setText(pedido.getChavePeso());
                pago.setSelected(pedido.isPago());
                entregue.setSelected(pedido.isEntregue());
                return pedido;
            } else {
                CaixaDeMensagem.mensagemErro("Erro", "Erro ao buscar pedido", "Pedido não encontrado.","botao-x.png");
                throw new IllegalArgumentException("Pedido não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao buscar pedido", "Código não encontrado.","botao-x.png");
        }
        return null;
    }
}

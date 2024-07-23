package bm.app.Model.pedidos;

import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.FormaPagamento;
import bm.app.Model.StatusPedido;
import bm.app.Model.cliente.Cliente;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class PedidoService {
    public Pedido criarPedido(String nome, BigDecimal valor, FormaPagamento formaPagamento, String entregador,
                              StatusPedido statusPedido, String chavePeso, boolean pedidoFeito, Cliente cliente) {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, nome, valor, formaPagamento.getFormaPagamento(),
                entregador, statusPedido.getStatusPedido(), chavePeso, cliente);
        try {
            boolean valido = Validacao.validarCamposAdicionarPedido(pedido, pedidoFeito);
            if (valido) {
                return pedido;
            }
        } catch (IllegalArgumentException e) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao adicionar pedido", "Por favor, preencha todos os campos obrigatórios.");
        }
        return null;
    }

    public void atualizarPedido(CheckBox pago, CheckBox entregue, TableView<PedidoTableView> tabela, ObservableList<PedidoTableView> lista,
                                PedidoDAO pedidoDAO) {
        PedidoTableView pedido = tabela.getSelectionModel().getSelectedItem();
        if (pago.isSelected() && entregue.isSelected()) {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.ENTREGUE, true, true);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else if (pago.isSelected()) {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.PAGO, true, false);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else if (entregue.isSelected()) {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.ENTREGUE, false, true);
            pedidoDAO.atualizarAndamento(pedidoDb);
        } else {
            Pedido pedidoDb = settarPedido(pedido, StatusPedido.PENDENTE, false, false);
            pedidoDAO.atualizarAndamento(pedidoDb);
        }
        tabela.refresh();
        AppUtils.atualizarTabelas(lista);
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
                    Pedido pedido = new Pedido(pedidoTableView);
                    pedidoDAO.removerPedido(pedido);
                }
                list.removeAll(removePedidoTableView);

            }
        }
    }
}

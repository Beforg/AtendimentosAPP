package bm.app.Model.pedidos;

import bm.app.Model.FormaPagamento;
import bm.app.Model.StatusPedido;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class PedidoService {
    public Pedido criarPedido(String nome, BigDecimal valor, FormaPagamento formaPagamento, String entregador,
                              StatusPedido statusPedido, String chavePeso, boolean pedidoFeito) {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, nome, valor, formaPagamento.getFormaPagamento(), LocalDateTime.now(), entregador, statusPedido.getStatusPedido(), chavePeso);
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

    public void atualizarPedido(CheckBox pago, CheckBox entregue, TableView<PedidoTableView> tabela, ObservableList<PedidoTableView> lista) {
        PedidoTableView pedido = tabela.getSelectionModel().getSelectedItem();
        if (pago.isSelected() && entregue.isSelected()) {
            pedido.setStatus(StatusPedido.ENTREGUE.getStatusPedido());
            pedido.setPago(true);
            pedido.setEntregue(true);
        } else if (pago.isSelected()) {
            pedido.setStatus(StatusPedido.PAGO.getStatusPedido());
            pedido.setPago(true);
            pedido.setEntregue(false);
        } else if (entregue.isSelected()) {
            pedido.setStatus(StatusPedido.ENTREGUE.getStatusPedido());
            pedido.setPago(false);
            pedido.setEntregue(true);
        } else {
            pedido.setStatus(StatusPedido.PENDENTE.getStatusPedido());
            pedido.setPago(false);
            pedido.setEntregue(false);
        }
        tabela.refresh();
        AppUtils.atualizarTabelas(lista);
    }
}

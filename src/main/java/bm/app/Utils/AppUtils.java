package bm.app.Utils;

import bm.app.Metodos.ValorTotal;
import bm.app.Model.FormaPagamento;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.util.Locale;

public class AppUtils {
    public static void adicionaItensChoiceBox(ChoiceBox<String> choiceBoxFormaPagamento) {
       for (FormaPagamento formaPagamento : FormaPagamento.values()) {
           choiceBoxFormaPagamento.getItems().add(formaPagamento.getFormaPagamento());
       }
    }

    public static void mostraDetalhesPedido(TableView<PedidoTableView> tabelaPedidos, CheckBox pago, CheckBox entregue,
                                            Label codigo, Label dataHora, Label chavePeso) {
       tabelaPedidos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               Pedido pedido = new Pedido(newValue);
                codigo.setText(pedido.getId().toString());
                dataHora.setText(pedido.getDataPedido().toString());
                chavePeso.setText(pedido.getChavePeso());
                pago.setSelected(pedido.verificaPago());
                entregue.setSelected(pedido.verificaEntregue());
                pago.setDisable(false);
                codigo.setVisible(true);
                dataHora.setVisible(true);
                chavePeso.setVisible(true);
                entregue.setDisable(false);

           } else {
               codigo.setVisible(false);
               dataHora.setVisible(false);
               chavePeso.setVisible(false);
               pago.setDisable(true);
               entregue.setDisable(true);
           }
       });
    }
    public static void configuraTabelaPedidos(TableView<PedidoTableView> tabelaPedidos, TableColumn<PedidoTableView, String> nomeCliente,
                                              TableColumn<PedidoTableView, String> entregador, TableColumn<PedidoTableView, String> statusCliente,
                                              TableColumn<PedidoTableView, BigDecimal> brl, TableColumn<PedidoTableView, BigDecimal> uyu,
                                              TableColumn<PedidoTableView, CheckBox> deleta, TableColumn<PedidoTableView, String> pagamento,
                                              ObservableList<PedidoTableView> list) {
        tabelaPedidos.setPlaceholder(new Label("Não há atendimentos registrados."));
        nomeCliente.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("nome"));
        nomeCliente.setCellFactory(TextFieldTableCell.forTableColumn());

        entregador.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("entregador"));
        entregador.setCellFactory(TextFieldTableCell.forTableColumn());

        statusCliente.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("status"));
        statusCliente.setCellFactory(ChoiceBoxTableCell.forTableColumn("Atendendo", "Pedido", "Entregue", "Não pago", "Pago"));
        statusCliente.setOnEditCommit(event -> {
            PedidoTableView pedidoTableView = event.getRowValue();
            pedidoTableView.setStatus(event.getNewValue());
            System.out.println(pedidoTableView.getStatus());
        });
        brl.setCellValueFactory(new PropertyValueFactory<PedidoTableView, BigDecimal>("brl"));
        brl.setCellFactory(column -> {
            MoneyTableCell brlCell = new MoneyTableCell(new Locale("pt", "BR"), "BRL");
            brlCell.setTabelaCliente(tabelaPedidos);
            return brlCell;
        });


        uyu.setCellValueFactory(new PropertyValueFactory<PedidoTableView, BigDecimal>("uyu"));
        uyu.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        uyu.setCellFactory(column -> {
            MoneyTableCell brlCell = new MoneyTableCell(new Locale("pt", "BR"), "BRL");
            brlCell.setTabelaCliente(tabelaPedidos);
            return brlCell;
        });
        deleta.setCellValueFactory(new PropertyValueFactory<PedidoTableView, CheckBox>("remover"));
        pagamento.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("formaPagamento"));
        pagamento.setCellFactory(ChoiceBoxTableCell.forTableColumn("Dinheiro", "Cartão", "Pix"));
        tabelaPedidos.setEditable(true);
        tabelaPedidos.setItems(list);

    }

    public static void configuraTabelaPedidosTotal(TableColumn<PedidoTotalTableView, Integer> clienteTotal,
                                                   TableColumn<PedidoTotalTableView, Integer> pedidosTotais,
                                                   TableColumn<PedidoTotalTableView, Integer> entreguesTot,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> valorTotal,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> brlRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> uyuRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> pixRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> recebidoCartao,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> valor_npg,
                                                   TableView<PedidoTotalTableView> tabelaTotal,
                                                   ObservableList<PedidoTotalTableView> listPedidosTotal,
                                                   TableView<PedidoTableView> tabelaPedidos,
                                                   String valorTotalReais,
                                                   BigDecimal valorTotalPix,
                                                   BigDecimal valorTotalPesos,
                                                   BigDecimal valorTotalPedidos,
                                                   int atendTotal,
                                                   BigDecimal valorNrecebido,
                                                   IntegerProperty pedidosProperty,
                                                   IntegerProperty entreguesProperty,
                                                   BigDecimal valorCartao,
                                                   TableColumn<PedidoTableView, BigDecimal> brl) {
        clienteTotal.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("atendimentos"));

        pedidosTotais.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("pedidos"));
        entreguesTot.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("entregues"));

        valorTotal.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorTotal"));
        valorTotal.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        brlRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorBrl"));
        brlRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        uyuRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorUyu"));
        uyuRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("es", "UY", "UYU")));

        pixRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorPix"));
        pixRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        recebidoCartao.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorPix"));
        recebidoCartao.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        valor_npg.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorNaoRecebido"));
        valor_npg.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));
        ValorTotal.vincularSoma(tabelaPedidos, brl,tabelaTotal,valorTotal);

        tabelaPedidos.getItems().addListener((ListChangeListener<? super PedidoTableView>) c -> {
            int pedidosCount = 0;
            int entregasCount = 0;
            for (PedidoTableView pedidoTableView : tabelaPedidos.getItems()) {
                if ("Pedido".equals(pedidoTableView.getStatus()) || "Entregue".equals(pedidoTableView.getStatus()) || "Não Pago".equals(pedidoTableView.getStatus()) || "Pago".equals(pedidoTableView.getStatus())) {
                    pedidosCount++;
                }


            }
            for (PedidoTableView pedidoTableView : tabelaPedidos.getItems()) {
                if (pedidoTableView.isEntregue()) {
                    entregasCount++;
                }
            }
            entreguesProperty.set(entregasCount);
            pedidosProperty.set(pedidosCount);
        });
        PedidoTotalTableView total = new PedidoTotalTableView(atendTotal, pedidosProperty.get(), valorTotalPedidos, new BigDecimal(valorTotalReais), valorTotalPix, valorTotalPesos, valorNrecebido, valorCartao, entreguesProperty.get());
        total.atendimentosProperty().bind(Bindings.size(tabelaPedidos.getItems()));
        total.pedidosProperty().bind(pedidosProperty);
        total.entreguesProperty().bind(entreguesProperty);
        listPedidosTotal.add(total);

        tabelaTotal.getItems().addAll(listPedidosTotal);
    }
    public static void limpaCamposAdicionarPedido(TextField nome, TextField valor, ChoiceBox<String> formaPagamento, CheckBox pedido) {
        nome.clear();
        valor.clear();
        formaPagamento.setValue(null);
        pedido.setSelected(false);
    }

    public static void liberaCamposAdicionarPedido(CheckBox adicionaPedido, TextField valorPedido, ChoiceBox<String> listaPagamento) {
        if (adicionaPedido.isSelected()) {
            valorPedido.setDisable(false);
            valorPedido.setText("0,00");
            listaPagamento.setDisable(false);
        } else {
            valorPedido.setDisable(true);
            valorPedido.setText("");
            listaPagamento.setDisable(true);
        }
    }
    public static void atualizarTabelas(ObservableList<PedidoTableView> list) {
        PedidoTableView novoPedidoTableView = new PedidoTableView();
        list.add(novoPedidoTableView);
        list.remove(novoPedidoTableView);
    }
}

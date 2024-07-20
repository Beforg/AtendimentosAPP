package bm.app.Metodos;

import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.math.BigDecimal;

public class ValorTotal {
    public static void vincularSoma(TableView<PedidoTableView> tabelaCliente, TableColumn<PedidoTableView, BigDecimal> brlColumn, TableView<PedidoTotalTableView> tabelaTotal, TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaCliente.getItems().stream()
                                .map(PedidoTableView::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaCliente.getItems());
        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularFalta(TableView<PedidoTableView> tabelaAtendimento, TableView<PedidoTotalTableView> tabelaTotal, TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaAtendimento.getItems().stream()
                                .filter(atendimento -> !atendimento.isPago())
                                .map(PedidoTableView::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaAtendimento.getItems());

        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaAtendimento.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularDinheiro(
        TableView<PedidoTableView> tabelaCliente,
        TableView<PedidoTotalTableView> tabelaTotal,
        TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn,
        String tipoPagamento
) {
    ObservableList<PedidoTableView> pedidosList = FXCollections.observableArrayList();

    tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
        pedidosList.clear();
        for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
            String pagamento = pedidoTableView.getFormaPagamento();
            Boolean pago = pedidoTableView.isPago();

            if (tipoPagamento.equals(pagamento) && pago != null && pago) {
                pedidosList.add(pedidoTableView);
            }
        }
    });

    pedidosList.addListener((ListChangeListener<PedidoTableView>) change -> {
        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        pedidosList.stream()
                                .map(PedidoTableView::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                pedidosList

        );

        BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
        valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
        tabelaTotal.refresh();
    });

    tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
        tabelaTotal.refresh();
    });
}

    public static void vincularUyu(
            TableView<PedidoTableView> tabelaCliente,
            TableColumn<PedidoTableView, String> pagamentoColumn,
            TableColumn<PedidoTableView, Boolean> pagoColumn,
            TableView<PedidoTotalTableView> tabelaTotal,
            TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn
    ) {
        ObservableList<PedidoTableView> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            clientesRelevantes.clear();
            for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(pedidoTableView);
                Boolean pago = pagoColumn.getCellData(pedidoTableView);

                if ("Pesos".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(pedidoTableView);
                }
            }
        });
        clientesRelevantes.addListener((ListChangeListener<PedidoTableView>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(PedidoTableView::getUyu)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });
        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularPix(
            TableView<PedidoTableView> tabelaCliente,
            TableColumn<PedidoTableView, String> pagamentoColumn,
            TableColumn<PedidoTableView, Boolean> pagoColumn,
            TableView<PedidoTotalTableView> tabelaTotal,
            TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn
    ) {
        ObservableList<PedidoTableView> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            clientesRelevantes.clear();
            for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(pedidoTableView);
                Boolean pago = pagoColumn.getCellData(pedidoTableView);

                if ("PIX".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(pedidoTableView);
                }
            }
        });

        clientesRelevantes.addListener((ListChangeListener<PedidoTableView>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(PedidoTableView::getBrl)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularCartao(
            TableView<PedidoTableView> tabelaCliente,
            TableColumn<PedidoTableView, String> pagamentoColumn,
            TableColumn<PedidoTableView, Boolean> pagoColumn,
            TableView<PedidoTotalTableView> tabelaTotal,
            TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn
    ) {
        ObservableList<PedidoTableView> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            clientesRelevantes.clear();
            for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(pedidoTableView);
                Boolean pago = pagoColumn.getCellData(pedidoTableView);

                if ("Cartão".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(pedidoTableView);
                } else if ("Outro".equals(pagamento)  && pago != null && pago) {
                    clientesRelevantes.add(pedidoTableView);
                }
            }
        });

        clientesRelevantes.addListener((ListChangeListener<PedidoTableView>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(PedidoTableView::getBrl)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
}



package bm.app.Metodos;

import bm.app.Model.Cliente;
import bm.app.Model.ClienteTotal;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.math.BigDecimal;

public class ValorTotal {
    public static void vincularSoma(TableView<Cliente> tabelaCliente, TableColumn<Cliente, BigDecimal> brlColumn, TableView<ClienteTotal> tabelaTotal, TableColumn<ClienteTotal, BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaCliente.getItems().stream()
                                .map(Cliente::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaCliente.getItems());
        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularFalta(TableView<Cliente> tabelaAtendimento, TableView<ClienteTotal> tabelaTotal, TableColumn<ClienteTotal, BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaAtendimento.getItems().stream()
                                .filter(atendimento -> !atendimento.isPago())
                                .map(Cliente::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaAtendimento.getItems());

        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaAtendimento.getItems().addListener((ListChangeListener<Cliente>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularBrl(
        TableView<Cliente> tabelaCliente,
        TableColumn<Cliente, String> pagamentoColumn,
        TableColumn<Cliente, Boolean> pagoColumn,
        TableView<ClienteTotal> tabelaTotal,
        TableColumn<ClienteTotal, BigDecimal> valorTotalColumn
) {
    ObservableList<Cliente> clientesRelevantes = FXCollections.observableArrayList();

    tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
        clientesRelevantes.clear();
        for (Cliente cliente : tabelaCliente.getItems()) {
            String pagamento = pagamentoColumn.getCellData(cliente);
            Boolean pago = pagoColumn.getCellData(cliente);

            if ("Reais".equals(pagamento) && pago != null && pago) {
                clientesRelevantes.add(cliente);
            }
        }
    });

    clientesRelevantes.addListener((ListChangeListener<Cliente>) change -> {
        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        clientesRelevantes.stream()
                                .map(Cliente::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                clientesRelevantes

        );

        BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
        valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
        tabelaTotal.refresh();
    });

    tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
        tabelaTotal.refresh();
    });
}
    public static void vincularUyu(
            TableView<Cliente> tabelaCliente,
            TableColumn<Cliente, String> pagamentoColumn,
            TableColumn<Cliente, Boolean> pagoColumn,
            TableView<ClienteTotal> tabelaTotal,
            TableColumn<ClienteTotal, BigDecimal> valorTotalColumn
    ) {
        ObservableList<Cliente> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            clientesRelevantes.clear();
            for (Cliente cliente : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(cliente);
                Boolean pago = pagoColumn.getCellData(cliente);

                if ("Pesos".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(cliente);
                }
            }
        });
        clientesRelevantes.addListener((ListChangeListener<Cliente>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(Cliente::getUyu)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });
        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularPix(
            TableView<Cliente> tabelaCliente,
            TableColumn<Cliente, String> pagamentoColumn,
            TableColumn<Cliente, Boolean> pagoColumn,
            TableView<ClienteTotal> tabelaTotal,
            TableColumn<ClienteTotal, BigDecimal> valorTotalColumn
    ) {
        ObservableList<Cliente> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            clientesRelevantes.clear();
            for (Cliente cliente : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(cliente);
                Boolean pago = pagoColumn.getCellData(cliente);

                if ("PIX".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(cliente);
                }
            }
        });

        clientesRelevantes.addListener((ListChangeListener<Cliente>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(Cliente::getBrl)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularCartao(
            TableView<Cliente> tabelaCliente,
            TableColumn<Cliente, String> pagamentoColumn,
            TableColumn<Cliente, Boolean> pagoColumn,
            TableView<ClienteTotal> tabelaTotal,
            TableColumn<ClienteTotal, BigDecimal> valorTotalColumn
    ) {
        ObservableList<Cliente> clientesRelevantes = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            clientesRelevantes.clear();
            for (Cliente cliente : tabelaCliente.getItems()) {
                String pagamento = pagamentoColumn.getCellData(cliente);
                Boolean pago = pagoColumn.getCellData(cliente);

                if ("Cartão".equals(pagamento) && pago != null && pago) {
                    clientesRelevantes.add(cliente);
                } else if ("Outro".equals(pagamento)  && pago != null && pago) {
                    clientesRelevantes.add(cliente);
                }
            }
        });

        clientesRelevantes.addListener((ListChangeListener<Cliente>) change -> {
            DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                            clientesRelevantes.stream()
                                    .map(Cliente::getBrl)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum(),
                    clientesRelevantes

            );

            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
            tabelaTotal.refresh();
        });

        tabelaCliente.getItems().addListener((ListChangeListener<Cliente>) change -> {
            tabelaTotal.refresh();
        });
    }
}



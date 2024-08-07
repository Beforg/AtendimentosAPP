package bm.app.Utils;

import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.notas.NotasClienteTableView;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormataTabelaMonetaria extends TableCell<PedidoTableView, BigDecimal> {

    private final TextField textField;
    private final NumberFormat currencyFormat;
    @Setter
    private TableView<PedidoTableView> tabelaCliente;
    @Setter
    private TableView<NotasClienteTableView> tabelaNotas;
    @Setter
    private PedidoDAO pedidoDAO = new PedidoDAO();

    public FormataTabelaMonetaria(Locale locale, String currencyCode) {
        if (currencyCode.equals("BRL")) {
            currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        } else if (currencyCode.equals("UYU")) {
            currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "UY"));
        } else {
            currencyFormat = NumberFormat.getCurrencyInstance(locale);
        }
        textField = new TextField();
        textField.setPromptText("Valor");
        textField.setOnAction(event -> commitEdit(new BigDecimal(textField.getText().replace(",", "."))));
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String valor = textField.getText().replace(",", ".");
                commitEdit(new BigDecimal(valor));

            }
        });
    }

    @Override
    protected void updateItem(BigDecimal item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                textField.setText(item.toString());
                setGraphic(textField);
                setText(null);
            } else {
                setText(currencyFormat.format(item));
                setGraphic(null);
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) return;
        textField.setText(getItem().toString());
        setGraphic(textField);
        String style = "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; " +
                "-fx-font-family: 'Yu Gothic Medium'; -fx-border-color: #000000; -fx-border-width: 0.3px; " +
                "-fx-border-radius: 8px; -fx-padding: 2.5px; -fx-pref-width: 120px";
        textField.setStyle(style);
        setText(null);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(currencyFormat.format(getItem()));
        setGraphic(null);
    }

    @Override
    public void commitEdit(BigDecimal newValue) {
        super.commitEdit(newValue);

        PedidoTableView pedidoTableView = this.tabelaCliente.getSelectionModel().getSelectedItem();
        Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoTableView.getId());

        if (pedidoTableView != null) {
            pedidoTableView.setBrl(newValue);
            pedidoTableView.setUyu(newValue.multiply(new BigDecimal(pedidoTableView.getChavePeso().replace(",","."))));
            pedidoTableView.setStatus("Pedido");
            pedido.atualizarValor(pedidoTableView);
            pedidoDAO.atualizarValorPedido(pedido);
            getTableView().refresh();
            AppUtils.atualizarTabelas(tabelaCliente.getItems());
            setText(currencyFormat.format(newValue));
            setGraphic(null);
        }
    }

}

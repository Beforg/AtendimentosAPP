package bm.app.Utils;

import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.scene.control.TableCell;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormataTabelaMonetariaTotal extends TableCell<PedidoTotalTableView, BigDecimal> {

    private final NumberFormat currencyFormat;


    public FormataTabelaMonetariaTotal(Locale locale) {
        currencyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    protected void updateItem(BigDecimal item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {

            String formattedPrice = currencyFormat.format(item);
            setText(formattedPrice);
        }
    }
}

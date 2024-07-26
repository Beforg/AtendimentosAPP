package bm.app.Controller;

import bm.app.Infra.dao.NotasClienteDAO;
import bm.app.Model.notas.NotasClienteTableView;
import bm.app.Utils.AppUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class NotasController implements Initializable {
    @FXML
    private TableView<NotasClienteTableView> tabelaNotas;

    @FXML
    private TableColumn<NotasClienteTableView, String> tcCliente,tcData,tcPedido,tcPeso,tcValor;

    private ObservableList<NotasClienteTableView> list = FXCollections.observableArrayList();
    private final NotasClienteDAO notasClienteDAO = new NotasClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppUtils.configuraTabelaNotasPendentes(tcCliente, tcData, tcValor, tcPeso,tcPedido, tabelaNotas, list);
        notasClienteDAO.carregarParaTabela(list);

    }
}

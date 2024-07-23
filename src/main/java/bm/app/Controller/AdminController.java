package bm.app.Controller;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Model.credenciamento.AdminService;
import bm.app.Model.credenciamento.AdminTableView;
import bm.app.Utils.AppUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private PasswordField pfSenhaCriar;

    @FXML
    private TableView<AdminTableView> tabelaAdm;

    @FXML
    private TableColumn<AdminTableView, String> tcId,tcNome;

    @FXML
    private TextField tfUUsuarioCriar,tfNomeCriar;

    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();
    private final AdminService adminService = new AdminService();
    private ObservableList<AdminTableView> list = FXCollections.observableArrayList();

    @FXML
    void cadastrar(ActionEvent event) {
        adminService.cadastrarAdmin(tfNomeCriar.getText(),tfUUsuarioCriar.getText(),pfSenhaCriar.getText(),credenciamentoDAO);
        AppUtils.limparCamposCadastroCredenciamento(pfSenhaCriar,tfNomeCriar,tfUUsuarioCriar);
    }

    @FXML
    void excluir(ActionEvent event) {
        adminService.excluirAdmin(tabelaAdm,tabelaAdm.getSelectionModel().getSelectedItem(),credenciamentoDAO);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppUtils.configuraTabelaAdmin(tabelaAdm,tcId,tcNome);
        adminService.listarAdminsTabela(tabelaAdm,credenciamentoDAO,list);
    }
}

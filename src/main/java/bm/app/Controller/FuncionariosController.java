package bm.app.Controller;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Model.credenciamento.FuncionariosService;
import bm.app.Model.credenciamento.FuncionariosTableView;
import bm.app.Utils.AppUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FuncionariosController implements Initializable {
    @FXML
    private PasswordField tfSenha;
    @FXML
    private TableView<FuncionariosTableView> tabelaFuncionarios;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcId;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcNome;
    @FXML
    private TextField tfUser,tfNome;

    private ObservableList<FuncionariosTableView> list = FXCollections.observableArrayList();
    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();
    private final FuncionariosService funcionariosService = new FuncionariosService();

    public void cadastrar(ActionEvent event) {
        funcionariosService.cadastrarFuncionario(tfNome.getText(),tfUser.getText(),tfSenha.getText(),credenciamentoDAO);
        AppUtils.limparCamposCadastroCredenciamento(tfSenha,tfNome,tfUser);
    }

    public void excluirCadastro(ActionEvent event) {
        funcionariosService.excluirFuncionario(tabelaFuncionarios,tabelaFuncionarios.getSelectionModel().getSelectedItem(),credenciamentoDAO);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppUtils.configuraTabelaFuncionarios(tabelaFuncionarios, tcId,tcNome);
        funcionariosService.listarFuncionariosTabela(tabelaFuncionarios,list,credenciamentoDAO);
    }
}

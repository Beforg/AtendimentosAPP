package bm.app.Controller;

import bm.app.Infra.configuration.ConfigUtil;
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
    private Button btExcluir;
    @FXML
    private TableView<FuncionariosTableView> tabelaFuncionarios;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcId;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcNome;
    @FXML
    private TextField tfUser,tfNome;
    @FXML
    private Tab tabFuncionario;
    private Credenciamento credenciamento;

    private ObservableList<FuncionariosTableView> list = FXCollections.observableArrayList();
    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();
    private final FuncionariosService funcionariosService = new FuncionariosService();

    public void cadastrar(ActionEvent event) {
        funcionariosService.cadastrarFuncionario(tfNome.getText(),tfUser.getText(),tfSenha.getText(),credenciamentoDAO);
        AppUtils.limparCamposCadastroCredenciamento(tfSenha,tfNome,tfUser);
        atualizarTabela();
    }

    public void excluirCadastro(ActionEvent event) {
        funcionariosService.excluirFuncionario(tabelaFuncionarios,tabelaFuncionarios.getSelectionModel().getSelectedItem(),credenciamentoDAO);
        atualizarTabela();
    }

    private void atualizarTabela() {
        funcionariosService.listarFuncionariosTabela(tabelaFuncionarios,list,credenciamentoDAO);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        credenciamento = AppController.credenciamento;
        ConfigUtil.permissoesCadastros(credenciamento,tabFuncionario,btExcluir);
        AppUtils.configuraTabelaFuncionarios(tcId,tcNome);
        atualizarTabela();
    }
}

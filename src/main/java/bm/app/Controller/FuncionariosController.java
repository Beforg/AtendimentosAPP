package bm.app.Controller;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Model.credenciamento.FuncionariosService;
import bm.app.Model.credenciamento.FuncionariosTableView;
import bm.app.Utils.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FuncionariosController {
    @FXML
    private Label labelNome,labelUser;
    @FXML
    private ListView<Credenciamento> listFuncionarios;
    @FXML
    private PasswordField pfNovaSenha;
    @FXML
    private TableView<FuncionariosTableView> tabelaFuncionarios;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcId;
    @FXML
    private TableColumn<FuncionariosTableView, String> tcNome;
    @FXML
    private PasswordField tfSenha;
    @FXML
    private TextField tfTrocaUsuariao,tfUser,tfTrocaNome,tfNome;
    @FXML
    private Button btEditar,btExcluir,btSalvar;

    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();
    private final FuncionariosService funcionariosService = new FuncionariosService();

    @FXML
    void cadastrar(ActionEvent event) {
        funcionariosService.cadastrarFuncionario(tfNome.getText(),tfUser.getText(),tfSenha.getText(),credenciamentoDAO);
    }

    @FXML
    void editarCadastro(ActionEvent event) {
        AppUtils.mostrarEdicaoFuncionario(labelNome,labelUser, btExcluir,btEditar,btSalvar,tfTrocaNome,tfTrocaUsuariao
                ,pfNovaSenha,false,false,true,true
                ,listFuncionarios.getSelectionModel().getSelectedItem());

    }

    @FXML
    void excluirCadastro(ActionEvent event) {

    }

    @FXML
    void salvarEdicaoCadastro(ActionEvent event) {
        AppUtils.mostrarEdicaoFuncionario(labelNome,labelUser, btExcluir,btEditar,btSalvar,tfTrocaNome,tfTrocaUsuariao
                ,pfNovaSenha,true,true,false,false
                ,listFuncionarios.getSelectionModel().getSelectedItem());
        funcionariosService.editarFuncionario(listFuncionarios.getSelectionModel().getSelectedItem(),
                tfTrocaNome.getText(),tfTrocaUsuariao.getText(),pfNovaSenha.getText(),credenciamentoDAO);
    }
}

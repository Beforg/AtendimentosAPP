package bm.app.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    @FXML
    private Label labelEndereço;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelTelefone;

    @FXML
    private TableView<?> tabelaClientes;
    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcNome;

    @FXML
    private Tab teste;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfComplemento;

    @FXML
    private TextField tfEditarBairro;

    @FXML
    private TextField tfEditarComplemento;

    @FXML
    private TextField tfEditarNome;

    @FXML
    private TextField tfEditarNumero;

    @FXML
    private TextField tfEditarRua;

    @FXML
    private TextField tfEditarTelefone;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfTelefone;

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void excluir(ActionEvent event) {

    }

    @FXML
    void salvarEdicao(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getTabs().remove(teste);
    }
}

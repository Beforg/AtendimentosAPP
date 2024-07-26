package bm.app.Controller;

import bm.app.Infra.dao.ClienteDAO;
import bm.app.Model.cliente.ClienteService;
import bm.app.Model.cliente.ClientesTableView;
import bm.app.Utils.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    @FXML
    private Label labelTelefone,labelNome, labelEndereco;

    @FXML
    private TableView<ClientesTableView> tabelaClientes;
    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<ClientesTableView, String> tcId,tcNome,tcEndereco,tcTelefone;
    @FXML
    private Tab tabEditarCliente,clienteCadastradosTab;

    @FXML
    private TextField tfBairro,tfComplemento,tfEditarBairro,tfEditarComplemento,tfEditarNome,tfEditarNumero,
            tfEditarRua,tfEditarTelefone,tfNome,tfNumero,tfRua,tfTelefone;

    private final ClienteService clienteService = new ClienteService();
    private final ClienteDAO clienteDAO = new ClienteDAO();


    @FXML
    void cadastrar(ActionEvent event) {
        clienteService.cadastrarCliente(tfNome.getText(),tfRua.getText(),tfNumero.getText(),tfBairro.getText()
                ,tfComplemento.getText(),tfTelefone.getText(),clienteDAO);
        atualizarTabela();
        tabPane.getSelectionModel().select(clienteCadastradosTab);
        AppUtils.limpaCamposCadastrarCliente(tfNome,tfRua,tfNumero,tfBairro,tfComplemento,tfTelefone);
    }

    @FXML
    void editar(ActionEvent event) {
        AppUtils.abrirEdicaoCliente(
                tabelaClientes
                ,tabPane
                ,tabEditarCliente,"Editar"
                ,tabelaClientes.getSelectionModel().getSelectedItem(),
                tfEditarRua,tfEditarNumero,tfEditarBairro,tfEditarComplemento,tfEditarNome,tfEditarTelefone);
    }

    @FXML
    void excluir(ActionEvent event) {
        clienteService.removerCliente(clienteDAO, tabelaClientes.getSelectionModel().getSelectedItem(), tabelaClientes);
        atualizarTabela();
    }

    @FXML
    void salvarEdicao(ActionEvent event) {
        AppUtils.abrirEdicaoCliente(
                tabelaClientes
                ,tabPane
                ,tabEditarCliente,"Fechar"
                ,tabelaClientes.getSelectionModel().getSelectedItem(),
                tfEditarRua,tfEditarNumero,tfEditarBairro,tfEditarComplemento,tfEditarNome,tfTelefone);
        clienteService.editarCliente(clienteDAO, tabelaClientes.getSelectionModel().getSelectedItem(),
                tabelaClientes,tfEditarNome,tfEditarRua,tfEditarNumero,tfEditarComplemento,tfEditarBairro,tfEditarTelefone);
        atualizarTabela();
        tabPane.getSelectionModel().select(clienteCadastradosTab);
    }

    private void atualizarTabela() {
        clienteService.listarClientes(clienteDAO, tabelaClientes.getItems(), tabelaClientes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clienteService.listarClientes(clienteDAO, tabelaClientes.getItems(), tabelaClientes);
        AppUtils.ocultaTabs(tabPane,tabEditarCliente);
        AppUtils.configuraTabelaClientes(tcId,tcNome,tcEndereco,tcTelefone,"Min");
        AppUtils.listenerTabelaClientes(tabelaClientes,labelNome,labelEndereco,labelTelefone);
    }
}

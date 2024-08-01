package bm.app.Utils;

import bm.app.Model.cliente.Cliente;
import bm.app.Model.cliente.ClientesTableView;
import bm.app.Model.credenciamento.AdminTableView;
import bm.app.Model.credenciamento.FuncionariosTableView;
import bm.app.Model.pedidos.Pedido;
import javafx.scene.control.*;

import java.time.LocalDate;

public class Validacao {
    public static boolean validarCamposAdicionarPedido(Pedido pedido, boolean pedidoFeitoCheckBox) {
        if (pedido.getNome().isEmpty() && pedidoFeitoCheckBox) {
            CaixaDeMensagem.mensagemErro("Erro", "Nome do pedido não pode ser vazio", "Por favor, insira um nome para o pedido.","botao-x.png");
            throw new IllegalArgumentException("Pedido no formato Inválido");
        } else {
            if (pedido.getFormaPagamento() == null || pedido.getChavePeso().isEmpty()) {
                CaixaDeMensagem.mensagemErro("Erro", "Campos obrigatórios não preenchidos", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
                throw new IllegalArgumentException("Pedido no formato Inválido");
            }
            return true;
        }
    }
    public static Cliente verificaComboBoxAdicionarCliente(ComboBox<Cliente> comboBox) {
        if (comboBox.getSelectionModel().isEmpty()) {
            return null;
        }
        return comboBox.getSelectionModel().getSelectedItem();
    }
    public static boolean verificaFuncionarioSelecionado(TableView<FuncionariosTableView> tabela) {
        FuncionariosTableView funcionariosTableView = tabela.getSelectionModel().getSelectedItem();
        return funcionariosTableView != null;
    }
    public static boolean verificaAdminSelecionado(TableView<AdminTableView> tabela) {
        AdminTableView admTableView = tabela.getSelectionModel().getSelectedItem();
        return admTableView != null;
    }
    public static boolean verificaClienteSelecionado(TableView<ClientesTableView> tabela) {
        ClientesTableView cliente = tabela.getSelectionModel().getSelectedItem();
        return cliente != null;
    }

    public static boolean validarCamposCliente(String nome, String rua) {
        return !nome.isEmpty() && !rua.isEmpty();
    }

    public static boolean validarCamposFuncionario(String nome, String username, String password) {
        return !nome.isEmpty() && !username.isEmpty() && !password.isEmpty();
    }

    public static boolean validaData(LocalDate value,Button adicionar) {
        if (value.isAfter(LocalDate.now())) {
            adicionar.setDisable(true);
            return false;
        } else if (value.isBefore(LocalDate.now())) {
            adicionar.setDisable(true);
            return true;
        } else if (value.isEqual(LocalDate.now())) {
            adicionar.setDisable(false);
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarBancoDeDados(TextField tfUrlBanco, TextField tfUser, PasswordField pfSenhaBanco) {
        if (tfUrlBanco.getText().isEmpty() || tfUser.getText().isEmpty() || pfSenhaBanco.getText().isEmpty()) {
            CaixaDeMensagem.mensagemErro("Erro", "Campos obrigatórios não preenchidos", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
            return false;
        }
        return true;
    }
}

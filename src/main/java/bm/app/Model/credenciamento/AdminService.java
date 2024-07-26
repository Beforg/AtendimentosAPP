package bm.app.Model.credenciamento;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;

public class AdminService {
    public void cadastrarAdmin(String nome, String username, String password, CredenciamentoDAO credenciamentoDAO) {
        if (!Validacao.validarCamposFuncionario(nome, username, password)) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar admin", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(nome,username,password, Cargo.ADMINISTRADOR);
        if (credenciamentoDAO.verificaUsernameExistente(credenciamento)) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar admin", "Username já existente.","botao-x.png");
            return;
        }
        credenciamentoDAO.criar(credenciamento);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Funcionário cadastrado", "Administrador cadastrado com sucesso.","done.png");
    }
    public void excluirAdmin(TableView<AdminTableView> tab, AdminTableView adminTableView, CredenciamentoDAO credenciamentoDAO) {
        boolean val = Validacao.verificaAdminSelecionado(tab);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Admin não selecionado", "Por favor, selecione um admin para excluir.","botao-x.png");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(adminTableView);
        credenciamentoDAO.excluir(credenciamento);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Exlcluir Admin", "Admin excluído com sucesso.","done.png");
    }
    public void listarAdminsTabela(TableView<AdminTableView> tabela,
                                   CredenciamentoDAO credenciamentoDAO,
                                   ObservableList<AdminTableView> list) {
        tabela.getItems().clear();
        credenciamentoDAO.listarAdmin(list);
        tabela.setItems(list);
    }

}

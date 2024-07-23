package bm.app.Model.credenciamento;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;

public class AdminService {
    public void cadastrarAdmin(String nome, String username, String password, CredenciamentoDAO credenciamentoDAO) {
        Credenciamento credenciamento = new Credenciamento(nome,username,password, Cargo.ADMINISTRADOR);
        credenciamentoDAO.criar(credenciamento);
    }
    public void excluirAdmin(TableView<AdminTableView> tab, AdminTableView adminTableView, CredenciamentoDAO credenciamentoDAO) {
        boolean val = Validacao.verificaAdminSelecionado(tab);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Admin não selecionado", "Por favor, selecione um admin para excluir.");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(adminTableView);
        credenciamentoDAO.excluir(credenciamento);
    }
    public void listarAdminsTabela(TableView<AdminTableView> tabela,
                                   CredenciamentoDAO credenciamentoDAO,
                                   ObservableList<AdminTableView> list) {
        credenciamentoDAO.listarAdmin(list);
        tabela.setItems(list);
    }

}

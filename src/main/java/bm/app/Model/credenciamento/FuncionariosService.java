package bm.app.Model.credenciamento;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class FuncionariosService {
    public void cadastrarFuncionario(String nome, String username, String password, CredenciamentoDAO credenciamentoDAO) {
        Credenciamento credenciamento = new Credenciamento(nome,username,password, Cargo.FUNCIONARIO);
        credenciamentoDAO.criar(credenciamento);
    }


    public void excluirFuncionario(TableView<FuncionariosTableView> tabela,FuncionariosTableView funcionariosTableView, CredenciamentoDAO credenciamentoDAO) {
        boolean val = Validacao.verificaFuncionarioSelecionado(tabela);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Funcionário não selecionado", "Por favor, selecione um funcionário para excluir.");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(funcionariosTableView);
        credenciamentoDAO.excluir(credenciamento);


    }

    public void listarFuncionariosTabela(TableView<FuncionariosTableView> tabela,
                                         ObservableList<FuncionariosTableView> list,
                                         CredenciamentoDAO credenciamentoDAO) {

        credenciamentoDAO.listarFuncionario(list);
        tabela.setItems(list);
    }
}

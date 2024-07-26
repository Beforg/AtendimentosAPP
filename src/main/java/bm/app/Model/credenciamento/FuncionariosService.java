package bm.app.Model.credenciamento;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class FuncionariosService {
    public void cadastrarFuncionario(String nome, String username, String password, CredenciamentoDAO credenciamentoDAO) {
        if (!Validacao.validarCamposFuncionario(nome, username, password)) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar funcionário", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(nome,username,password, Cargo.FUNCIONARIO);
        if (credenciamentoDAO.verificaUsernameExistente(credenciamento)) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar funcionário", "Username já existente.","botao-x.png");
            return;
        }
        credenciamentoDAO.criar(credenciamento);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Funcionário cadastrado", "Funcionário cadastrado com sucesso.","done.png");
    }


    public void excluirFuncionario(TableView<FuncionariosTableView> tabela,FuncionariosTableView funcionariosTableView, CredenciamentoDAO credenciamentoDAO) {
        boolean val = Validacao.verificaFuncionarioSelecionado(tabela);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Funcionário não selecionado", "Por favor, selecione um funcionário para excluir.","botao-x.png");
            return;
        }
        Credenciamento credenciamento = new Credenciamento(funcionariosTableView);
        credenciamentoDAO.excluir(credenciamento);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Funcionário cadastrado", "Funcionário excluído com sucesso.","done.png");


    }

    public void listarFuncionariosTabela(TableView<FuncionariosTableView> tabela,
                                         ObservableList<FuncionariosTableView> list,
                                         CredenciamentoDAO credenciamentoDAO) {
        tabela.getItems().clear();
        credenciamentoDAO.listarFuncionario(list);
        tabela.setItems(list);
    }
}

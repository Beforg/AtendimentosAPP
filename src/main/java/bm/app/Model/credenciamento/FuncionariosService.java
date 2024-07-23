package bm.app.Model.credenciamento;

import bm.app.Infra.dao.CredenciamentoDAO;

public class FuncionariosService {
    public void cadastrarFuncionario(String nome, String username, String password, CredenciamentoDAO credenciamentoDAO) {
        Credenciamento credenciamento = new Credenciamento(nome,username,password, Cargo.FUNCIONARIO);
        credenciamentoDAO.criar(credenciamento);
    }

    public void editarFuncionario(Credenciamento credenciamentoAntigo, String nome, String username, String password,CredenciamentoDAO credenciamentoDAO) {
        credenciamentoAntigo.setNome(nome);
        credenciamentoAntigo.setUsername(username);
        credenciamentoAntigo.setPassword(password);
        credenciamentoDAO.criar(credenciamentoAntigo);
    }

    public void excluirFuncionario(Credenciamento credenciamento, CredenciamentoDAO credenciamentoDAO) {
        credenciamentoDAO.excluir(credenciamento);

    }

    public void listarFuncionarios() {
    }
}

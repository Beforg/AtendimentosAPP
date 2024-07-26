package bm.app.Model.cliente;

import bm.app.Infra.dao.ClienteDAO;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClienteService {
    public void cadastrarCliente(String nome,
                                 String rua,
                                 String numero,
                                 String bairro,
                                 String complemento,
                                 String telefone,
                                 ClienteDAO clienteDAO) {
        if (!Validacao.validarCamposCliente(nome, rua)) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar cliente", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
            return;
        }
        String endereco = AppUtils.enderecoBuilder(rua, numero, bairro, complemento);
        Cliente cliente = new Cliente(nome, telefone,endereco);
        clienteDAO.cadastrarCliente(cliente);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Cadastro de Clientes", "Cliente cadastrado com  sucesso.","done.png");

    }
    public void listarClientes(ClienteDAO clienteDAO, ObservableList<ClientesTableView> clientesTableViewsList,
                               TableView<ClientesTableView> tableView) {
        tableView.getItems().clear();
        clienteDAO.listarClientes(clientesTableViewsList);
        tableView.setItems(clientesTableViewsList);
    }
    public void removerCliente(ClienteDAO clienteDAO, ClientesTableView clientes, TableView<ClientesTableView> clientesTableView) {
        boolean val = Validacao.verificaClienteSelecionado(clientesTableView);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Cliente não selecionado", "Por favor, selecione um cliente para excluir.","botao-x.png");
            return;
        }
        try {
            Cliente cliente = new Cliente(clientes);
            clienteDAO.removerCliente(cliente);
            CaixaDeMensagem.mensagemInformacao("Sucesso", "Excluir Cliente", "Cliente excluído com sucesso","done.png");
        } catch (RuntimeException e) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao excluir cliente", "Erro: existem registros de pedidos vinculados ao Cliente. ","botao-x.png" );
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage());


        }

    }

    public void editarCliente(ClienteDAO clienteDAO, ClientesTableView clientes,
                              TableView<ClientesTableView> clientesTableView,
                              TextField nome,
                              TextField rua,
                              TextField numero,
                              TextField complemento,
                              TextField bairro,
                              TextField telefone) {
        if (!Validacao.validarCamposCliente(nome.getText(), rua.getText())) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao cadastrar cliente", "Por favor, preencha todos os campos obrigatórios.","botao-x.png");
            return;
        }
        Cliente cliente = new Cliente(clientes);
        cliente.setNome(nome.getText());
        String endereco = AppUtils.enderecoBuilder(rua.getText(), numero.getText(), bairro.getText(), complemento.getText());
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone.getText());

        clienteDAO.editarCliente(cliente);
        CaixaDeMensagem.mensagemInformacao("Sucesso", "Editar Cliente", "Informações alteradas com sucesso.","done.png");

    }
}

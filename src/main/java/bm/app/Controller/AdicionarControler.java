package bm.app.Controller;

import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.FormaPagamento;
import bm.app.Model.StatusPedido;
import bm.app.Model.cliente.Cliente;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoService;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import bm.app.View.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AdicionarControler implements Initializable {
    @FXML
    private Button cancelarBot;
    @FXML
    private TextField adicionaNome;
    @FXML
    private CheckBox adicionaPedido,continuaAdd;
    @FXML
    private TextField valorPedido;
    @FXML
    private ChoiceBox<String> listaPagamento;
    @FXML
    private TextField chave_peso;
    @FXML
    private ComboBox<Cliente> cbSelecionarCliente;

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final PedidoService pedidoService = new PedidoService();
    private StatusPedido statusPedido = StatusPedido.PENDENTE;
    public static String peso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppUtils.adicionaItensChoiceBox(listaPagamento);
        chave_peso.setText(peso);
    }


    public void cancelar() throws IOException {
        boolean verifica = CaixaDeMensagem.mensagemConfirmacao("Cancelar atendimento", "Tem certeza que deseja cancelar?", "", cancelarBot);
        if (verifica) {
            AppController.verificaJanela = false;
            Stage stage = (Stage) cancelarBot.getScene().getWindow();
            stage.close();
        }

    }
    public void adicionar() {
        if (adicionaPedido.isSelected()) {
            statusPedido = StatusPedido.EM_ANDAMENTO;
        }

        Cliente cliente = Validacao.verificaComboBoxAdicionarCliente(cbSelecionarCliente);
        Pedido pedido = pedidoService.criarPedido(adicionaNome.getText(),
                new BigDecimal(valorPedido.getText().replace(",", ".")),
                FormaPagamento.valueOf(listaPagamento.getValue().toUpperCase().replace("Ã", "A")),
                "", statusPedido, chave_peso.getText(), adicionaPedido.isSelected(), cliente);
        AppController.adicionarAtendimento(pedido);
        pedidoDAO.criarPedido(pedido);

        if(!continuaAdd.isSelected()) {
            AppController.verificaJanela = false;
            Stage stage = (Stage) cancelarBot.getScene().getWindow();
            stage.close();
        } else {
            AppUtils.limpaCamposAdicionarPedido(adicionaNome, valorPedido, listaPagamento, adicionaPedido);
        }
    }

    public void pedidoMarcado() {
        AppUtils.liberaCamposAdicionarPedido(adicionaPedido, valorPedido, listaPagamento);
    }
    public void teste() {
        System.out.println("Teste");
    }


}

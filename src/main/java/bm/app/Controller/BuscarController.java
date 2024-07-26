package bm.app.Controller;

import bm.app.Infra.dao.NotasClienteDAO;
import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.notas.NotasClienteService;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoService;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BuscarController implements Initializable {

    @FXML
    private Tab buscarPedidoTab;

    @FXML
    private CheckBox cbEntregue,cbPago;

    @FXML
    private Label labelData,labelValor,labelChave,labelNomeCliente;

    @FXML
    private Tab resultadoPedidoTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField tfCodigo;
    private Pedido pedido;
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final PedidoService pedidoService = new PedidoService();
    private final NotasClienteService notasClienteService = new NotasClienteService();
    private final NotasClienteDAO notasClienteDAO = new NotasClienteDAO();

    @FXML
    void alteraEntregue(ActionEvent event) {
        pedidoService.atualizarPedidoBuscador(cbPago,cbEntregue,pedido,pedidoDAO,notasClienteDAO,notasClienteService);
    }

    @FXML
    void alteraPago(ActionEvent event) {
        boolean val = CaixaDeMensagem.mensagemConfirmacao("Atenção", "Alterar pedido para pago?", "Após alterar para pago o pedido será removido das notas pendentes caso exista.","aviso.png");
       if (val) {
           pedidoService.atualizarPedidoBuscador(cbPago,cbEntregue,pedido,pedidoDAO,notasClienteDAO,notasClienteService);
           CaixaDeMensagem.mensagemInformacao("Sucesso", "Pedido alterado para pago", "Pedido alterado com sucesso.","done.png");
       } else {
           cbPago.setSelected(false);
       }

    }

    @FXML
    void buscarPedido(ActionEvent event) {
        this.pedido = pedidoService.buscarPorId(pedidoDAO,tfCodigo.getText(), labelNomeCliente,labelValor,labelData,labelChave,
                cbPago,cbEntregue);
        if (pedido != null) {
            AppUtils.tabResultadoBuscaPedido(tabPane, resultadoPedidoTab, buscarPedidoTab,pedido, "Abrir");
        }
    }
    @FXML
    void voltar(ActionEvent event) {
         AppUtils.tabResultadoBuscaPedido(tabPane, resultadoPedidoTab, buscarPedidoTab,pedido, "Fechar");
    }

    @FXML
    void cancelarBusca(ActionEvent event) {
        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppUtils.ocultaTabs(tabPane,resultadoPedidoTab);
    }
}

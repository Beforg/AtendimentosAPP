package bm.app.Controller;

import bm.app.Metodos.*;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoService;
import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.View.AdicionarView;
import bm.app.View.ViewService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private  Button botaoFinalizar;
    @FXML
    public TableView<PedidoTableView> tabelaPedidos;
    @FXML
    private TableColumn<PedidoTableView, BigDecimal> brl;
    @FXML
    private TableColumn<PedidoTotalTableView, BigDecimal> brlRecebido,valor_npg;
    @FXML
    private TableColumn<PedidoTotalTableView, Integer> clienteTotal,pedidosTotais,entreguesTot;
    @FXML
    private TableColumn<PedidoTableView, String> nomeCliente,pagamento,statusCliente,entregador;
    @FXML
    private TableView<PedidoTotalTableView> tabelaTotal;
    @FXML
    private TableColumn<PedidoTableView, BigDecimal> uyu;
    @FXML
    private TableColumn<PedidoTotalTableView, BigDecimal> uyuRecebido,recebidoCartao,valorTotal,pixRecebido;
    @FXML
    public TextField valorPeso,nomeFuncionario;
    @FXML
    private TableColumn<PedidoTableView, CheckBox> deleta;
    @FXML
    static ObservableList<PedidoTableView> list = FXCollections.observableArrayList();
    @FXML
    private ImageView promocoes_img;
    @FXML
    private TextArea anotacoes;
    @FXML
    private ObservableList<PedidoTotalTableView> listPedidosTotal = FXCollections.observableArrayList();
    @FXML
    private Label labelCodigoPedido, labelChavePesoPedido, labelDataHoraPedido;
    @FXML
    private CheckBox cbPagoPedido, cbEntreguePedido;

    private final PedidoService pedidoService = new PedidoService();

    int atendTotal = 0;
    public static Boolean verificaJanela = false;

    IntegerProperty pedidosProperty = new SimpleIntegerProperty(0);
    IntegerProperty entreguesProperty = new SimpleIntegerProperty(0);
    String valorTotalReais = "0";

    BigDecimal valorTotalPesos = new BigDecimal("0");
    BigDecimal valorTotalPix = new BigDecimal("0");
    BigDecimal valorTotalPedidos = new BigDecimal("0");
    BigDecimal valorNrecebido = new BigDecimal("0");
    BigDecimal valorCartao = new BigDecimal("0");



    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy" +"," + " HH:mm:ss", Locale.getDefault());
    String horaAtual = sdf.format(new Date());

    public void ajuda() throws IOException {
        ViewService viewService = new ViewService();
        Stage ajudaViewStage = viewService.ajudaView();
        ajudaViewStage.show();
    }
    public void sobre() throws IOException {
        ViewService viewService = new ViewService();
        Stage sobreViewStage = viewService.sobreView();
        sobreViewStage.show();
    }
    @FXML
    public void salvarTabelaEmPDF() {
        SalvarPDF salvarPDF = new SalvarPDF();
        salvarPDF.salvarPDF(tabelaTotal, list, horaAtual,nomeFuncionario, valorPeso, anotacoes, botaoFinalizar);
    }

    public void adicionar(ActionEvent e) throws IOException {
        AdicionarView adicionarView = new AdicionarView();
        Stage adicionar = adicionarView.telaAdicionar(verificaJanela,valorPeso);
        if (!verificaJanela) {
            adicionar.setOnCloseRequest(event -> {
                verificaJanela =  false;
            });
            verificaJanela = true;
        } else {
            CaixaDeMensagem.mensagemErro("Erro", "Já existe uma operação em aberto", "Por favor, finalize a operação atual antes de continuar");
        }
    }

    public void botaoRemover(ActionEvent event){
        RemoverCliente removerCliente = new RemoverCliente();
        removerCliente.removerCliente(list);
    }

    public static void adicionarAtendimento(Pedido pedido) {
        PedidoTableView novoPedidoTableView = new PedidoTableView(pedido);
        list.add(novoPedidoTableView);
        verificaJanela = false;
    }
//    public static void atualizarAtendimento() {
//        PedidoTableView novoPedidoTableView = new PedidoTableView();
//        list.add(novoPedidoTableView);
//        list.remove(novoPedidoTableView);
//
//    }

    public void editarNome(TableColumn.CellEditEvent<PedidoTableView, String> nomeClienteTroca) {
        EditName editName = new EditName();
        editName.editName(tabelaPedidos, nomeClienteTroca);
    }
    public void editarEntregador(TableColumn.CellEditEvent<PedidoTableView, String> nomeClienteTroca) {
        PedidoTableView pedidoTableView = tabelaPedidos.getSelectionModel().getSelectedItem();
        String oldName = pedidoTableView.getEntregador();
        System.out.println(oldName);
        pedidoTableView.setEntregador(nomeClienteTroca.getNewValue());
        if (pedidoTableView.getEntregador().equals("")) {
            pedidoTableView.setEntregador(oldName);
            tabelaPedidos.refresh();
        }
    }

    public void carregar_imagem(){
        RadioButtons.loadImage(promocoes_img);
    }

    public void alterarPago() {
        pedidoService.atualizarPedido(cbPagoPedido, cbEntreguePedido, tabelaPedidos,list);
    }

    public void alterarEntregue() {
        pedidoService.atualizarPedido(cbPagoPedido, cbEntreguePedido, tabelaPedidos,list);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppUtils.configuraTabelaPedidos(tabelaPedidos, nomeCliente, entregador, statusCliente,
                brl, uyu, deleta, pagamento, list);

        AppUtils.mostraDetalhesPedido(tabelaPedidos, cbPagoPedido, cbEntreguePedido,
                labelCodigoPedido, labelDataHoraPedido,labelChavePesoPedido);

        AppUtils.configuraTabelaPedidosTotal(
                clienteTotal,
                pedidosTotais,
                entreguesTot,
                valorTotal,
                brlRecebido,
                uyuRecebido,
                 pixRecebido,
                recebidoCartao,
                valor_npg,
                tabelaTotal,
                 listPedidosTotal,
                 tabelaPedidos,
                valorTotalReais,
                valorTotalPix,
                valorTotalPesos,
                valorTotalPedidos,
                atendTotal,
                valorNrecebido,
                pedidosProperty,
                entreguesProperty,
                valorCartao,
                brl);

        ValorTotal.vincularDinheiro(tabelaPedidos, tabelaTotal, brlRecebido, "Reais");
        ValorTotal.vincularDinheiro(tabelaPedidos, tabelaTotal, uyuRecebido, "Pesos");
        ValorTotal.vincularDinheiro(tabelaPedidos, tabelaTotal, recebidoCartao, "Cartão");
        ValorTotal.vincularDinheiro(tabelaPedidos, tabelaTotal, pixRecebido, "Pix");


        promocoes_img.setOnMouseClicked(event -> {
            if (promocoes_img.getImage() == null) {
                System.out.println("Nenhuma imagem selecionada");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nenhuma imagem selecionada");
                alert.setHeaderText("Nenhuma imagem selecionada");
                alert.setContentText("Por favor, selecione uma imagem para visualizar");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("aviso.png"));
                alert.showAndWait();
            } else {
                Stage stage = new Stage();
                ImageView imageView = new ImageView(promocoes_img.getImage());
                imageView.setFitWidth(600);
                imageView.setFitHeight(600);

                StackPane layout = new StackPane();
                layout.getChildren().add(imageView);

                Scene scene = new Scene(layout);
                stage.setScene(scene);
                stage.show();
            }
        });

    }
    public void registro_rb() {

    }
    public void promo_rb() {

    }
}

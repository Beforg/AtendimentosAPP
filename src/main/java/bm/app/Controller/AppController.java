package bm.app.Controller;

import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.notas.NotasService;
import bm.app.Utils.GerarRelatorio;
import bm.app.Model.notas.Notas;
import bm.app.Utils.ValorTotal;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private  Button botaoFinalizar,btEditarNota;
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
    public TextField valorPeso,nomeFuncionario,tfTituloNota;
    @FXML
    private TableColumn<PedidoTableView, CheckBox> deleta;
    @FXML
    private static ObservableList<PedidoTableView> list = FXCollections.observableArrayList();
    @FXML
    private TextArea anotacoes;
    @FXML
    private ObservableList<PedidoTotalTableView> listPedidosTotal = FXCollections.observableArrayList();
    @FXML
    private Label labelCodigoPedido, labelChavePesoPedido, labelDataHoraPedido;
    @FXML
    private CheckBox cbPagoPedido, cbEntreguePedido;
    @FXML
    private ImageView imvAdicionarNota, imvEditarNota, imvRemoverNota;
    @FXML
    private ListView<Notas> listaDeNotas;
    @FXML
    private Pane paneAdicionarTarefas;

    private final PedidoService pedidoService = new PedidoService();
    private final NotasService notasService = new NotasService();
    private int atendTotal = 0;
    private ObservableList<Notas> listNotas = FXCollections.observableArrayList();
    public static Boolean verificaJanela = false;
    private final PedidoDAO pedidoDAO = new PedidoDAO();

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

    public static void adicionarAtendimento(Pedido pedido) {
        PedidoTableView novoPedidoTableView = new PedidoTableView(pedido);
        list.add(novoPedidoTableView);
        verificaJanela = false;
    }
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
        pedidoService.removerPedido(list);
    }
    public void editarNome(TableColumn.CellEditEvent<PedidoTableView, String> nomeClienteTroca) {
        PedidoTableView ptv = AppUtils.editarNome(tabelaPedidos, nomeClienteTroca);
        Pedido pedido = new Pedido(ptv);
        pedidoDAO.atualizarNome(pedido);
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

    }
    public void alterarPago() {
        pedidoService.atualizarPedido(cbPagoPedido, cbEntreguePedido, tabelaPedidos,list);
    }
    public void alterarEntregue() {
        pedidoService.atualizarPedido(cbPagoPedido, cbEntreguePedido, tabelaPedidos,list);
    }
    public void registro_rb() {

    }
    public void promo_rb() {

    }
    public void funcionariosView() throws IOException {
        ViewService.funcionarioView();
    }
    public void admView() throws IOException {
        ViewService.administradorView();
    }
    public void cadastrarClienteView() throws IOException {
        ViewService.clientesView();
    }
    public void gerenciamentoClienteView() throws IOException {
        ViewService.clienteCadastroView();
    }
    public void notasPendentesView() throws IOException {
        ViewService.notasView();
    }
    public void fecharCaixa() {
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        gerarRelatorio.salvarPDF(tabelaTotal, list, horaAtual,nomeFuncionario, valorPeso, anotacoes, botaoFinalizar);
    }
    public void buscarDiaView() {

    }
    public void hoverAdicionarNota() {
        AppUtils.hoverImagem(imvAdicionarNota, "/ui/add-hov.png");
    }
    public void hoverEditarNota() {
        AppUtils.hoverImagem(imvEditarNota, "/ui/edit-hv.png");
    }
    public void hoverRemoverNota() {
        AppUtils.hoverImagem(imvRemoverNota, "/ui/delete-hv.png");
    }
    public void exitAdicionarNota() {
        AppUtils.hoverImagem(imvAdicionarNota, "/ui/add.png");
    }
    public void exitEditarNota() {
        AppUtils.hoverImagem(imvEditarNota, "/ui/edit.png");
    }
    public void exitRemoverNota() {
        AppUtils.hoverImagem(imvRemoverNota, "/delete.png");
    }
    public void adicionarNotaView() {
        AppUtils.adicionarNotasView(paneAdicionarTarefas, listaDeNotas,true,false, tfTituloNota);
    }
    public void voltarAdicionarView() {
        AppUtils.adicionarNotasView(paneAdicionarTarefas, listaDeNotas,false,true, tfTituloNota);
    }
    public void adicionarNota() {
       notasService.criarNota(tfTituloNota.getText(), anotacoes.getText(), listNotas);
        voltarAdicionarView();
        AppUtils.limpaCamposNotas(tfTituloNota, anotacoes);
    }
    public void editarNotaView() {
        AppUtils.editarNotasView(paneAdicionarTarefas, listaDeNotas, tfTituloNota, anotacoes,btEditarNota);
    }
    public void removerNota() {
        notasService.removerNota(listaDeNotas, listNotas);
    }
    public void editarNotaExistente() {
        notasService.editarNota(listaDeNotas, tfTituloNota, anotacoes, paneAdicionarTarefas);
    }
    public void atualizaStatus() {
        PedidoTableView pedidoTableView = tabelaPedidos.getSelectionModel().getSelectedItem();
        Pedido pedido = new Pedido(pedidoTableView);
        pedidoDAO.atualizarStatus(pedido);
        System.out.println("asd");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pedidoDAO.carregarPedidoHoje(list);
        AppUtils.configuraLista(listaDeNotas, listNotas);
        AppUtils.configuraTabelaPedidos(tabelaPedidos, nomeCliente, entregador, statusCliente,
                brl, uyu, deleta, pagamento, list,pedidoDAO);

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
        ValorTotal.vincularFalta(tabelaPedidos, tabelaTotal, valor_npg);


    }

}

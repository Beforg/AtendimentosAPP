package bm.app.Controller;

import bm.app.Infra.configuration.ConfigUtil;
import bm.app.Infra.dao.NotasClienteDAO;
import bm.app.Infra.dao.NotasDAO;
import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.anotacoes.NotasService;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Model.notas.NotasClienteService;
import bm.app.Utils.GerarRelatorio;
import bm.app.Model.anotacoes.Notas;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoService;
import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class AppController implements Initializable {
    @FXML
    private  Button botaoFinalizar,btEditarNota, add_botao;
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
    public TextField nomeFuncionario,tfTituloNota;
    @FXML
    private TableColumn<PedidoTableView, CheckBox> deleta;
    @FXML
    private static ObservableList<PedidoTableView> list = FXCollections.observableArrayList();
    @FXML
    private TextArea anotacoes;
    @FXML
    private ObservableList<PedidoTotalTableView> listPedidosTotal = FXCollections.observableArrayList();
    @FXML
    private Label labelCodigoPedido, labelChavePesoPedido, labelDataHoraPedido,valorPeso,labelNomeConta;
    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox cbPagoPedido, cbEntreguePedido;
    @FXML
    private ImageView imvAdicionarNota, imvEditarNota, imvRemoverNota;
    @FXML
    private ListView<Notas> listaDeNotas;
    @FXML
    private Pane paneAdicionarTarefas;
    @FXML
    private MenuItem menuAdministrador;

    private final PedidoService pedidoService = new PedidoService();
    private final NotasService notasService = new NotasService();
    private int atendTotal = 0;
    private ObservableList<Notas> listNotas = FXCollections.observableArrayList();
    public static Boolean verificaJanela = false;
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final NotasDAO notasDAO = new NotasDAO();
    private final NotasClienteService notasClienteService = new NotasClienteService();
    private final NotasClienteDAO notasClienteDAO = new NotasClienteDAO();
    public static Credenciamento credenciamento;

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
        Stage ajudaViewStage = ViewService.ajudaView();
        ajudaViewStage.show();
    }
    public void sobre() throws IOException {
        Stage sobreViewStage = ViewService.sobreView();
        sobreViewStage.show();
    }
    public void adicionar() throws IOException {
        Stage stage = ViewService.telaAdicionar(verificaJanela,valorPeso);
        stage.showAndWait();
//        AdicionarView adicionarView = new AdicionarView();
//        Stage adicionar = adicionarView.telaAdicionar(verificaJanela,valorPeso);
//        if (!verificaJanela) {
//            adicionar.setOnCloseRequest(event -> {
//                verificaJanela =  false;
//            });
//            verificaJanela = true;
//        } else {
//            CaixaDeMensagem.mensagemErro("Erro", "Já existe uma operação em aberto", "Por favor, finalize a operação atual antes de continuar");
//        }
    }
    public void botaoRemover(ActionEvent event){
        pedidoService.removerPedido(list,pedidoDAO);
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
    public void alterarPago() {
        pedidoService.atualizarPedido(cbPagoPedido,cbEntreguePedido , tabelaPedidos,list,pedidoDAO);
    }
    public void alterarEntregue() {
        pedidoService.atualizarPedido(cbPagoPedido,cbEntreguePedido , tabelaPedidos,list,pedidoDAO);
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
    public void notasPendentesView() throws IOException {
        Stage stage = ViewService.notasPendentesView();
        stage.showAndWait();
    }
    public void fecharCaixa() {
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        gerarRelatorio.salvarPDF(tabelaTotal, list, horaAtual,labelNomeConta, valorPeso, anotacoes, botaoFinalizar);
        List<Pedido> list = notasClienteDAO.carregaPedidosDoDia();
        System.out.println(list);
        notasClienteService.registrarNotasCliente(list, notasClienteDAO);

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
        btEditarNota.setVisible(false);
    }
    public void voltarAdicionarView() {
        AppUtils.adicionarNotasView(paneAdicionarTarefas, listaDeNotas,false,true, tfTituloNota);
    }
    public void adicionarNota() {
       notasService.criarNota(tfTituloNota.getText(), anotacoes.getText(), listNotas, notasDAO);
        voltarAdicionarView();
        AppUtils.limpaCamposNotas(tfTituloNota, anotacoes);
    }
    public void editarNotaView() {
        AppUtils.editarNotasView(paneAdicionarTarefas, listaDeNotas, tfTituloNota, anotacoes,btEditarNota);
    }
    public void removerNota() {
        notasService.removerNota(listaDeNotas, listNotas, notasDAO);
    }
    public void editarNotaExistente() {
        notasService.editarNota(listaDeNotas, tfTituloNota, anotacoes, paneAdicionarTarefas, notasDAO);
    }
    private void carregarChavePeso() {
        valorPeso.setText(ConfigUtil.getProperty("chavePeso"));
    }

    @FXML
    void carregaPedidosPorData(ActionEvent event) {
        if(Validacao.validaData(datePicker.getValue(),add_botao)) {
            list.clear();
            pedidoDAO.carregarPedido(list, datePicker.getValue());
            listNotas.clear();
            notasDAO.listarNotasDia(listaDeNotas, datePicker.getValue());
        } else {
            CaixaDeMensagem.mensagemErro("Erro", "Data inválida", "Por favor, selecione uma data válida","botao-x.png");
        }
    }

    @FXML
    void chavePesoView() throws IOException {
        Stage stage = ViewService.chavePesoView();
        stage.showAndWait();
        carregarChavePeso();
    }

    @FXML
    void consultarPedido() throws IOException {
        Stage stage = ViewService.buscarPedido();
        stage.showAndWait();
    }

    @FXML
    void getCodigo() {
        AppUtils.codigoToClipboard(labelCodigoPedido);
    }

    @FXML
    void trocarUser() throws IOException {
        Stage stage = (Stage) nomeFuncionario.getScene().getWindow();
        stage.close();
        Stage stage1 = ViewService.loginView();
        stage1.show();
    }
    @FXML
    void codigoPedidoHover() {
        labelCodigoPedido.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #f2f2f2; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-text-fill: black");
    }
    @FXML
    void codigoPedidoHoverOff() {
        labelCodigoPedido.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: white");
    }
    @FXML
    void addPedidoEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
           adicionar();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigUtil.permissoesApp(credenciamento,menuAdministrador);
        datePicker.setValue(LocalDate.now());
        labelNomeConta.setText(credenciamento.getNome());
        AppUtils.atualizarTabelas(list);
        carregarChavePeso();
        notasDAO.listarNotasHoje(listaDeNotas);
        pedidoDAO.carregarPedido(list, LocalDate.now());
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

        AppUtils.vincularDinheiro(tabelaPedidos, tabelaTotal, brlRecebido, "Reais",false);
        AppUtils.vincularDinheiro(tabelaPedidos, tabelaTotal, uyuRecebido, "Pesos",true);
        AppUtils.vincularDinheiro(tabelaPedidos, tabelaTotal, recebidoCartao, "Cartão",false);
        AppUtils.vincularCartao(tabelaPedidos, tabelaTotal, recebidoCartao,false);
        AppUtils.vincularDinheiro(tabelaPedidos, tabelaTotal, pixRecebido, "Pix",false);
        AppUtils.vincularFalta(tabelaPedidos, tabelaTotal, valor_npg);


    }

}

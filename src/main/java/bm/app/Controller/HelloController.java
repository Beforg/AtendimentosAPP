package bm.app.Controller;

import bm.app.Metodos.*;
import bm.app.Model.Cliente;
import bm.app.Model.ClienteTotal;
import bm.app.View.AdicionarView;
import bm.app.View.MenuView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private Label labelViewInfo;
    @FXML
    private MenuBar barraMenu;
    @FXML
    private AnchorPane painelFundo;
    @FXML
    private TableColumn<Cliente, String> entregador;
    @FXML
    private  Button botaoFinalizar;
    @FXML
    public TableView<Cliente> tabelaCliente;

    @FXML
    private TableColumn<Cliente, BigDecimal> brl;

    @FXML
    private TableColumn<ClienteTotal, BigDecimal> brlRecebido;
    @FXML
    private TableColumn<ClienteTotal, BigDecimal> valor_npg;

    @FXML
    private TableColumn<ClienteTotal, Integer> clienteTotal;

    @FXML
    private TableColumn<Cliente, String> nomeCliente;

    @FXML
    private TableColumn<Cliente, String> pagamento;

    @FXML
    private TableColumn<Cliente, Boolean> pago;

    @FXML
    private TableColumn<ClienteTotal, Integer> pedidosTotais;

    @FXML
    private TableColumn<Cliente, String> statusCliente;
    @FXML
    private TableColumn<Cliente, Boolean> entregue;

    @FXML
    private TableView<ClienteTotal> tabelaTotal;

    @FXML
    private TableColumn<Cliente, BigDecimal> uyu;

    @FXML
    private TableColumn<ClienteTotal, BigDecimal> uyuRecebido;
    @FXML
    private TableColumn<ClienteTotal, BigDecimal> recebidoCartao;
    @FXML
    private TableColumn<ClienteTotal, BigDecimal> valorTotal;
    @FXML
    private TableColumn<ClienteTotal, BigDecimal> pixRecebido;
    @FXML
    public TextField valorPeso;
    @FXML
    private TableColumn<Cliente, CheckBox> deleta;
    @FXML
    private TextField nomeFuncionario;
    @FXML
    private ImageView pdf;
    @FXML
    private  ImageView add;
    @FXML
    private ImageView delete;
    @FXML
    private TableColumn<ClienteTotal, Integer> entreguesTot;
    @FXML
    static ObservableList<Cliente> list = FXCollections.observableArrayList(

    );
    @FXML
    private ImageView promocoes_img;
    @FXML
    private TextArea anotacoes;
    @FXML
    private ObservableList<ClienteTotal> clientesTotal = FXCollections.observableArrayList();
    @FXML
    private Button botao_carregar;
    IntegerProperty pedidosProperty = new SimpleIntegerProperty(0);
    IntegerProperty entreguesProperty = new SimpleIntegerProperty(0);
    int atendTotal = 0;
    String valorTotalReais = "0";
    BigDecimal valorTotalPesos = new BigDecimal("0");
    BigDecimal valorTotalPix = new BigDecimal("0");
    BigDecimal valorTotalPedidos = new BigDecimal("0");
    BigDecimal valorNrecebido = new BigDecimal("0");
    BigDecimal valorCartao = new BigDecimal("0");
    public static Boolean verificaJanela = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy" +"," + " HH:mm:ss", Locale.getDefault());
    String horaAtual = sdf.format(new Date());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*@Css básico@*/
//        valor_npg.setStyle("-fx-text-fill: red;");
//        brlRecebido.setStyle("-fx-text-fill: #006400;");
//        uyuRecebido.setStyle("-fx-text-fill: #006400;");
//        recebidoCartao.setStyle("-fx-text-fill: #006400;");
//        pixRecebido.setStyle("-fx-text-fill: #006400;");
//        valorTotal.setStyle("-fx-text-fill: #2F4F4F;");
//
//        painelFundo.setStyle("-fx-background-color: #2E8B57;");
//        barraMenu.setStyle("-fx-background-color: #008080; -fx-font-size: 13px; -fx-font-family: Arial, sans-serif;" +
//                "-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-border-color: #008080; -fx-border-width: 0px 0px 1px 0px; -fx-border-style: solid;");
        tabelaCliente.setPlaceholder(new Label("Não há atendimentos registrados."));
//        tabelaCliente.setStyle("-fx-font-size: 14px;"+
//                "-fx-focus-color: transparent;" +
//                "-fx-faint-focus-color: t1ransparent;" +
//                "-fx-control-inner-background: #ffffff;" +
//                "-fx-selection-bar: #b3d9ff;" +
//                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);" +
//                "-fx-border-color: #2E8B57;");
//        tabelaCliente.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal) {
//                tabelaCliente.setStyle(
//                        "-fx-font-size: 14px;"+
//                        "-fx-focus-color: #b3d9ff;" +
//                                "-fx-faint-focus-color: #b3d9ff;"+
//                                "-fx-control-inner-background: #ffffff;" +
//                                "-fx-selection-bar: #b3d9ff;" +
//                                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);");
//
//            } else {
//                tabelaCliente.setStyle(
//                        "-fx-font-size: 14px;"+
//                        "-fx-focus-color: transparent;" +
//                                "-fx-faint-focus-color: transparent;"+
//                                "-fx-control-inner-background: #ffffff;" +
//                                "-fx-selection-bar: #b3d9ff;" +
//                                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);");
//
//            }
//        });

        /*@ Icones @*/

        Image image_add = new Image(String.valueOf(new File("add_order.png")));
        Image image_del = new Image(String.valueOf(new File("remove_order.png")));
        Image image_pdf = new Image(String.valueOf(new File("save_pdf.png")));
        Image iconeBotao = new Image("ajuda.png");

        ImageView iconeBotaoView = new ImageView(iconeBotao);
        iconeBotaoView.setFitWidth(24);
        iconeBotaoView.setFitHeight(20);
        botao_carregar.setStyle("-fx-background-color: #13aa52; -fx-border-color: black; -fx-border-width: 0px 0px 1px 0px; -fx-border-style: solid;");
        botao_carregar.setGraphic(iconeBotaoView);

        pdf.setImage(image_pdf);
        add.setImage(image_add);
        delete.setImage(image_del);

        /*@ Colunas @*/


        valorPeso.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                valorPeso.getParent().requestFocus();
            }
        });

        nomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        nomeCliente.setCellFactory(TextFieldTableCell.forTableColumn());

        entregador.setCellValueFactory(new PropertyValueFactory<Cliente, String>("entregador"));
        entregador.setCellFactory(TextFieldTableCell.forTableColumn());

        statusCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("status"));
        statusCliente.setCellFactory(ChoiceBoxTableCell.forTableColumn("Atendendo", "Pedido", "Entregue", "Não pago", "Pago"));
        statusCliente.setOnEditCommit(event -> {
            Cliente cliente = event.getRowValue();
            cliente.setStatus(event.getNewValue());
            System.out.println(cliente.getStatus());
        });



        entregue.setCellValueFactory(new PropertyValueFactory<Cliente, Boolean>("entregue"));
        entregue.setCellFactory(column -> new CheckBoxTableCell<Cliente, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                TableRow<Cliente> linha = getTableRow();
                Cliente cliente = (Cliente) getTableRow().getItem();

                if (!isEmpty() && cliente != null) {
                    System.out.println(cliente.getNome());
                    if (item != null && item && !cliente.isPago()) {
                        linha.setStyle("-fx-background-color: salmon;");

                    } else if (item != null && item && cliente.isPago()) {
                        linha.setStyle("-fx-background-color: greenyellow;");

                    } else {
                        if (item != null && cliente.isPago()) {
                            System.out.println(cliente.getStatus());
                            linha.setStyle("-fx-background-color: greenyellow;");
                        } else {
                            linha.setStyle("");
                        }

                    }

                }

            }
        });
        brl.setCellValueFactory(new PropertyValueFactory<Cliente, BigDecimal>("brl"));
        brl.setCellFactory(column -> new MoneyTableCell(new Locale("pt", "BR")));


        uyu.setCellValueFactory(new PropertyValueFactory<Cliente, BigDecimal>("uyu"));
        uyu.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        uyu.setCellFactory(column -> new MoneyTableCell(new Locale("es", "UY", "UYU")));
        uyu.setEditable(false);

        pago.setCellValueFactory(new PropertyValueFactory<Cliente, Boolean>("pago"));
        pago.setCellFactory(column -> new CheckBoxTableCell<Cliente, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);


                TableRow<Cliente> linha = getTableRow();
                Cliente cliente = (Cliente) getTableRow().getItem();
                if (!isEmpty() && cliente != null) {

                    if (cliente.isEntregue() && item != null && item) {
                        linha.setStyle("-fx-background-color: greenyellow;");

                        System.out.println(cliente.getStatus());
                    } else if (item != null && item) {
                        linha.setStyle("-fx-background-color: greenyellow;");

                    } else {
                        if (item != null && cliente.isEntregue()) {
                            linha.setStyle("-fx-background-color: salmon;");;


                        } else {
                            linha.setStyle("");
                            System.out.println("atendendo/pedido");
                        }
                    }
                }


            }
        });


        deleta.setCellValueFactory(new PropertyValueFactory<Cliente, CheckBox>("remover"));


        valorPeso.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                valorPeso.requestFocus();
                valorPeso.setText("");
            }
        });

        pagamento.setCellValueFactory(new PropertyValueFactory<Cliente, String>("formaPagamento"));
        pagamento.setCellFactory(ChoiceBoxTableCell.forTableColumn("Cartão", "Pesos", "Reais", "PIX", "Outros"));





        // =

        tabelaCliente.setEditable(true);
        tabelaCliente.setItems(list);


        /*@ Tabela dos valores Totais ============================================================================= @*/
        clienteTotal.setCellValueFactory(new PropertyValueFactory<ClienteTotal, Integer>("atendimentos"));

        pedidosTotais.setCellValueFactory(new PropertyValueFactory<ClienteTotal, Integer>("pedidos"));
        entreguesTot.setCellValueFactory(new PropertyValueFactory<ClienteTotal, Integer>("entregues"));

        valorTotal.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorTotal"));
        valorTotal.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        brlRecebido.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorBrl"));
        brlRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        uyuRecebido.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorUyu"));
        uyuRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("es", "UY", "UYU")));

        pixRecebido.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorPix"));
        pixRecebido.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        recebidoCartao.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorPix"));
        recebidoCartao.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));

        valor_npg.setCellValueFactory(new PropertyValueFactory<ClienteTotal, BigDecimal>("valorNaoRecebido"));
        valor_npg.setCellFactory(column -> new MoneyTableCellTotal(new Locale("pt", "BR")));


        //@**Somar valores totais**@//

        ValorTotal.vincularSoma(tabelaCliente, brl,tabelaTotal,valorTotal);
        ValorTotal.vincularBrl(tabelaCliente,pagamento,pago,tabelaTotal,brlRecebido);
        ValorTotal.vincularUyu(tabelaCliente,pagamento,pago,tabelaTotal,uyuRecebido);
        ValorTotal.vincularPix(tabelaCliente,pagamento,pago,tabelaTotal,pixRecebido);
        ValorTotal.vincularCartao(tabelaCliente,pagamento,pago,tabelaTotal,recebidoCartao);
        ValorTotal.vincularFalta(tabelaCliente, tabelaTotal, valor_npg);


        //@**Contador de Pedidos**@//

        tabelaCliente.getItems().addListener((ListChangeListener<? super Cliente>) c -> {
            int pedidosCount = 0;
            int entregasCount = 0;
            for (Cliente cliente : tabelaCliente.getItems()) {
                if ("Pedido".equals(cliente.getStatus()) || "Entregue".equals(cliente.getStatus()) || "Não Pago".equals(cliente.getStatus()) || "Pago".equals(cliente.getStatus())) {
                    pedidosCount++;
                }


            }
            for (Cliente cliente : tabelaCliente.getItems()) {
                if (cliente.isEntregue()) {
                    entregasCount++;
                }
            }
            entreguesProperty.set(entregasCount);
            pedidosProperty.set(pedidosCount);
        });


        /*@ Inicialização tabela ============================================================================= @*/
        ClienteTotal total = new ClienteTotal(atendTotal, pedidosProperty.get(), valorTotalPedidos, new BigDecimal(valorTotalReais), valorTotalPix, valorTotalPesos, valorNrecebido, valorCartao, entreguesProperty.get());
        total.atendimentosProperty().bind(Bindings.size(tabelaCliente.getItems()));
        total.pedidosProperty().bind(pedidosProperty);
        total.entreguesProperty().bind(entreguesProperty);
        clientesTotal.add(total);

        tabelaTotal.getItems().addAll(clientesTotal);
        tabelaCliente.setRowFactory(tableView -> {
            TableRow<Cliente> row = new TableRow<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty && item != null && item.getEntregue()) {
                        getStyleClass().add("entregue");

                    } else {
                        getStyleClass().remove("entregue");

                    }
                }
            };

            return row;
        });

        /*@ Selecionar imagem ============================================================================= @*/

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
    public void ajuda() throws IOException {
        MenuView menuView = new MenuView();
        Stage ajudaViewStage = menuView.ajudaView();
        ajudaViewStage.show();
    }
    public void sobre() throws IOException {
    MenuView menuView = new MenuView();
    Stage sobreViewStage = menuView.sobreView();
    sobreViewStage.show();
    }
    @FXML
    public void salvarTabelaEmPDF() {
        SalvarPDF salvarPDF = new SalvarPDF();
        salvarPDF.salvarPDF(tabelaTotal, list, horaAtual,nomeFuncionario, valorPeso, anotacoes, botaoFinalizar);
    }


    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setTitle("Cancelar atendimento");
        alert.setContentText("Tem certeza que deseja cancelar?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("aviso.png"));
        alert.getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst()
                .ifPresent(buttonType -> {
                    Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
                    button.setDefaultButton(false);

                });

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logout sucesso");
            stage.close();
        }
    }


    public void adicionar(ActionEvent e) throws IOException {
        AdicionarView adicionarView = new AdicionarView();
        Stage adicionar = adicionarView.telaAdicionar(verificaJanela,valorPeso);
        if (!verificaJanela) {
            adicionar.setOnCloseRequest(event -> {
                event.consume();
                logout(adicionar);
                verificaJanela =  false;
            });
            verificaJanela = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("Ocorreu um erro na aplicação");
            alert.setContentText("Já existe uma operação em aberto");
            alert.getButtonTypes().stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .ifPresent(buttonType -> {
                        Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
                        button.setDefaultButton(false);

                    });
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-size: 14px; -fx-font-family: Arial, sans-serif;");
            alert.showAndWait();
        }
    }

    public void botaoRemover(ActionEvent event){
        RemoverCliente removerCliente = new RemoverCliente();
        removerCliente.removerCliente(list);
    }

    public static void adicionarAtendimento(String nome, String status, BigDecimal brl, BigDecimal uyu, String formaPagamento, Boolean pago, Boolean entregue,String entregador) {
        Cliente novoCliente = new Cliente(nome, status, brl, uyu, formaPagamento, pago, entregue, entregador);
        list.add(novoCliente);
        verificaJanela = false;
    }
    public static void atualizarAtendimento(String nome, String status, BigDecimal brl, BigDecimal uyu, String formaPagamento, Boolean pago, Boolean entregue, String entregador) {
        Cliente novoCliente = new Cliente(nome, status, brl, uyu, formaPagamento, pago, entregue,entregador);
        list.add(novoCliente);
        list.remove(novoCliente);

    }

    public void editarNome(TableColumn.CellEditEvent<Cliente, String> nomeClienteTroca) {
        EditName editName = new EditName();
        editName.editName(tabelaCliente, nomeClienteTroca);
    }
    public void editarEntregador(TableColumn.CellEditEvent<Cliente, String> nomeClienteTroca) {
        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();
        String oldName = cliente.getEntregador();
        System.out.println(oldName);
        cliente.setEntregador(nomeClienteTroca.getNewValue());
        if (cliente.getEntregador().equals("")) {
            cliente.setEntregador(oldName);
            tabelaCliente.refresh();
        }
    }

public class MoneyTableCell extends TableCell<Cliente, BigDecimal> {

    private final TextField textField;
    private final NumberFormat currencyFormat;


    public MoneyTableCell(Locale locale) {
        currencyFormat = NumberFormat.getCurrencyInstance(locale);
        textField = new TextField();
        textField.setPromptText("Valor");
        textField.setOnAction(event -> commitEdit(new BigDecimal(textField.getText().replace(",","."))));
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String valor = textField.getText().replace(",",".");
                commitEdit(new BigDecimal(valor));

            }
        });
    }

    @Override
    protected void updateItem(BigDecimal item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            String formattedPrice = currencyFormat.format(item);
            setText(formattedPrice);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) return;
        setGraphic(textField);
        textField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: Arial, sans-serif; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
        textField.setText(getItem().toString());
        textField.selectAll();
        textField.requestFocus();

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        updateItem(getItem(), isEmpty());

    }

    @Override
    public void commitEdit(BigDecimal newValue) {
        super.commitEdit(newValue);

        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();

        if (cliente != null) {
            cliente.setBrl(newValue);
            cliente.setUyu(newValue.multiply(new BigDecimal(valorPeso.getText().replace(",","."))));
            cliente.setStatus("Pedido");
            getTableView().refresh();
        }
    }



}
    public static class MoneyTableCellTotal extends TableCell<ClienteTotal, BigDecimal> {

        private final NumberFormat currencyFormat;


        public MoneyTableCellTotal(Locale locale) {
            currencyFormat = NumberFormat.getCurrencyInstance(locale);
        }

        @Override
        protected void updateItem(BigDecimal item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {

                String formattedPrice = currencyFormat.format(item);
                setText(formattedPrice);
            }
        }
    }

    public void carregar_imagem(){
        RadioButtons.loadImage(promocoes_img);
    }
    public void registro_rb(){
        RadioButtons.anotacoesView(anotacoes, promocoes_img, botao_carregar, labelViewInfo);
    }
    public void promo_rb(){
        RadioButtons.promoView(anotacoes, promocoes_img, botao_carregar, labelViewInfo);
    }
    public void teste(){

    }

}

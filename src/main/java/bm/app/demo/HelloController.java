package bm.app.demo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private Label labelViewInfo;
    @FXML
    private Button add_botao;
    @FXML
    private MenuBar barraMenu;
    @FXML
    private AnchorPane painelFundo;
    @FXML
    private  Button remover_botao;
    @FXML
    private TableColumn<Cliente, String> entregador;
    @FXML
    private  Button botaoFinalizar;
    @FXML
    private TableView<Cliente> tabelaCliente;

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
    private TextField valorPeso;
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
    int entreguesTabela = 0;
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

        Image iconeBotao = new Image("carregar.png");
        ImageView iconeBotaoView = new ImageView(iconeBotao);
        iconeBotaoView.setFitWidth(24);
        iconeBotaoView.setFitHeight(20);
        botao_carregar.setStyle("-fx-background-color: #13aa52; -fx-border-color: black; -fx-border-width: 0px 0px 1px 0px; -fx-border-style: solid;");
        botao_carregar.setGraphic(iconeBotaoView);
        /* Style colunas TabelaTotal ================================================================================*/

        valor_npg.setStyle("-fx-text-fill: red;");
        brlRecebido.setStyle("-fx-text-fill: #006400;");
        uyuRecebido.setStyle("-fx-text-fill: #006400;");
        recebidoCartao.setStyle("-fx-text-fill: #006400;");
        pixRecebido.setStyle("-fx-text-fill: #006400;");
        valorTotal.setStyle("-fx-text-fill: #2F4F4F;");


        /*===========================================================================================================*/
        painelFundo.setStyle("-fx-background-color: #2E8B57;");
        barraMenu.setStyle("-fx-background-color: #008080; -fx-font-size: 13px; -fx-font-family: Arial, sans-serif;" +
                "-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-border-color: #008080; -fx-border-width: 0px 0px 1px 0px; -fx-border-style: solid;");
        tabelaCliente.setPlaceholder(new Label("Não há atendimentos registrados."));
        tabelaCliente.setStyle("-fx-font-size: 14px;"+
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: t1ransparent;" +
                "-fx-control-inner-background: #ffffff;" + /* Defina a cor de fundo interna desejada */
                "-fx-selection-bar: #b3d9ff;" + /* Defina a cor da barra de seleção desejada */
                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);" +
                "-fx-border-color: #2E8B57;");
        tabelaCliente.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tabelaCliente.setStyle(
                        "-fx-font-size: 14px;"+
                        "-fx-focus-color: #b3d9ff;" +
                                "-fx-faint-focus-color: #b3d9ff;"+
                                "-fx-control-inner-background: #ffffff;" + /* Defina a cor de fundo interna desejada */
                                "-fx-selection-bar: #b3d9ff;" + /* Defina a cor da barra de seleção desejada */
                                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);" /* Defina a cor da barra de seleção quando não estiver focada */);

            } else {
                tabelaCliente.setStyle(
                        "-fx-font-size: 14px;"+
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;"+
                                "-fx-control-inner-background: #ffffff;" + /* Defina a cor de fundo interna desejada */
                                "-fx-selection-bar: #b3d9ff;" + /* Defina a cor da barra de seleção desejada */
                                "-fx-selection-bar-non-focused: derive(#b3d9ff, -20%);" /* Defina a cor da barra de seleção quando não estiver focada */);

            }
        });


        add_botao.setStyle(
                "-fx-background-color: #13aa52; " +
                        "-fx-border-color: #13aa52; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                        "-fx-color: #fff; " +
                        "-fx-cursor: pointer; " +
                        "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 400; " +
                        "-fx-outline: none; " +
                        "-fx-padding: 10px 25px; " +
                        "-fx-text-align: center; " +
                        "-fx-translate-y: 0; " +
                        "-fx-transition: translate 150ms, box-shadow 150ms; " +
                        "-fx-user-select: none; " +
                        "-webkit-user-select: none; " +
                        "-fx-touch-action: manipulation;"
        );

        add_botao.setOnMouseEntered(event -> {
            add_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                    "-fx-box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0; " +
                            "-fx-translate-y: -2px;"
            );
        });

        add_botao.setOnMouseExited(event -> {
            add_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                    "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-translate-y: 0;"
            );
        });
        add_botao.setStyle(
                "-fx-background-color: #13aa52; " +
                        "-fx-border-color: #13aa52; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                        "-fx-color: #fff; " +
                        "-fx-cursor: pointer; " +
                        "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 400; " +
                        "-fx-outline: none; " +
                        "-fx-padding: 10px 25px; " +
                        "-fx-text-align: center; " +
                        "-fx-translate-y: 0; " +
                        "-fx-transition: translate 150ms, box-shadow 150ms; " +
                        "-fx-user-select: none; " +
                        "-webkit-user-select: none; " +
                        "-fx-touch-action: manipulation;"
        );

        add_botao.setOnMouseEntered(event -> {
            add_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0; " +
                            "-fx-translate-y: -2px;"
            );
        });

        add_botao.setOnMouseExited(event -> {
            add_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-translate-y: 0;"
            );
        });
        //========================================================================================
        remover_botao.setStyle(
                "-fx-background-color: #13aa52; " +
                        "-fx-border-color: #13aa52; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                        "-fx-color: #fff; " +
                        "-fx-cursor: pointer; " +
                        "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 400; " +
                        "-fx-outline: none; " +
                        "-fx-padding: 10px 25px; " +
                        "-fx-text-align: center; " +
                        "-fx-translate-y: 0; " +
                        "-fx-transition: translate 150ms, box-shadow 150ms; " +
                        "-fx-user-select: none; " +
                        "-webkit-user-select: none; " +
                        "-fx-touch-action: manipulation;"
        );

        remover_botao.setOnMouseEntered(event -> {
            remover_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0; " +
                            "-fx-translate-y: -2px;"
            );
        });

        remover_botao.setOnMouseExited(event -> {
            remover_botao.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-translate-y: 0;"
            );
        });
        //========================================================================================

        botaoFinalizar.setStyle(
                "-fx-background-color: #13aa52; " +
                        "-fx-border-color: #13aa52; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                        "-fx-color: #fff; " +
                        "-fx-cursor: pointer; " +
                        "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 400; " +
                        "-fx-outline: none; " +
                        "-fx-padding: 10px 25px; " +
                        "-fx-text-align: center; " +
                        "-fx-translate-y: 0; " +
                        "-fx-transition: translate 150ms, box-shadow 150ms; " +
                        "-fx-user-select: none; " +
                        "-webkit-user-select: none; " +
                        "-fx-touch-action: manipulation;"
        );

        botaoFinalizar.setOnMouseEntered(event -> {
            botaoFinalizar.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0; " +
                            "-fx-translate-y: -2px;"
            );
        });

        botaoFinalizar.setOnMouseExited(event -> {
            botaoFinalizar.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-translate-y: 0;"
            );
        });
        botaoFinalizar.setStyle(
                "-fx-background-color: #13aa52; " +
                        "-fx-border-color: #13aa52; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                        "-fx-color: #fff; " +
                        "-fx-cursor: pointer; " +
                        "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 400; " +
                        "-fx-outline: none; " +
                        "-fx-padding: 10px 25px; " +
                        "-fx-text-align: center; " +
                        "-fx-translate-y: 0; " +
                        "-fx-transition: translate 150ms, box-shadow 150ms; " +
                        "-fx-user-select: none; " +
                        "-webkit-user-select: none; " +
                        "-fx-touch-action: manipulation;"
        );

        botaoFinalizar.setOnMouseEntered(event -> {
            botaoFinalizar.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0; " +
                            "-fx-translate-y: -2px;"
            );
        });

        botaoFinalizar.setOnMouseExited(event -> {
            botaoFinalizar.setStyle(
                    "-fx-background-color: #13aa52; " +
                            "-fx-border-color: #13aa52; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-background-radius: 4px; " +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-color: #fff; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: \"Akzidenz Grotesk BQ Medium\", -apple-system, BlinkMacSystemFont, sans-serif; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: 400; " +
                            "-fx-outline: none; " +
                            "-fx-padding: 10px 25px; " +
                            "-fx-text-align: center; " +
                            "-fx-translate-y: 0; " +
                            "-fx-transition: translate 150ms, box-shadow 150ms; " +
                            "-fx-user-select: none; " +
                            "-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation;" +
                            "-fx-box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0; " +
                            "-fx-translate-y: 0;"
            );
        });

        //=================================================Icones/Imagens=======================================
        Image image_add = new Image(String.valueOf(new File("add_order.png")));
        Image image_del = new Image(String.valueOf(new File("remove_order.png")));
        Image image_pdf = new Image(String.valueOf(new File("save_pdf.png")));

        pdf.setImage(image_pdf);
        add.setImage(image_add);
        delete.setImage(image_del);

        //=====================================================================================================


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

        entregue.setCellValueFactory(new PropertyValueFactory<Cliente, Boolean>("entregue"));
        entregue.setCellFactory(column -> new CheckBoxTableCell<Cliente, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                TableRow<Cliente> linha = getTableRow();
                if (!isEmpty()) {
                    Cliente cliente = (Cliente) getTableRow().getItem();
                    if (item != null && item && cliente != null && !cliente.isPago()) {
                        linha.setStyle("-fx-background-color: salmon;");
                    } else {
                        if (item != null && cliente != null && cliente.isPago()) {
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
                if (!isEmpty()) {
                    Cliente cliente = (Cliente) getTableRow().getItem();

                    if (cliente != null && cliente.isEntregue() && item != null && item) {
                        linha.setStyle("-fx-background-color: greenyellow;");
                    } else if (cliente != null && item != null && item) {
                        linha.setStyle("-fx-background-color: greenyellow;");
                    } else {
                        if (item != null && cliente != null && cliente.isEntregue()) {
                            linha.setStyle("-fx-background-color: salmon;");

                        } else {
                            linha.setStyle("");
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


        //=================================================TabelaSomatoria:=======================================
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


        //@Adicinando objeto tabela total\\
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
        FXMLLoader segundaTela = new FXMLLoader(getClass().getResource("ajudaTela.fxml"));
        Scene tela2 = new Scene(segundaTela.load());
        Stage stageAdicionar = new Stage();
        stageAdicionar.setTitle("Ajuda");
        stageAdicionar.setScene(tela2);
        stageAdicionar.show();
        Image icon = new Image("ajuda.png");
        stageAdicionar.getIcons().add(icon);
        stageAdicionar.setResizable(false);
    }
    public void sobre() throws IOException {
        FXMLLoader segundaTela = new FXMLLoader(getClass().getResource("telaSobre.fxml"));
        Scene tela2 = new Scene(segundaTela.load());
        Stage stageAdicionar = new Stage();
        stageAdicionar.setTitle("Sobre");
        stageAdicionar.setScene(tela2);
        stageAdicionar.show();
        Image icon = new Image("sobre-nos.png");
        stageAdicionar.getIcons().add(icon);
        stageAdicionar.setResizable(false);
    }
    @FXML
    public void salvarTabelaEmPDF() {
        TableColumn<ClienteTotal, Integer> colunaAtendimentos = (TableColumn<ClienteTotal, Integer>) tabelaTotal.getColumns().get(0);
        String atendimentos = String.valueOf(colunaAtendimentos.getCellData(tabelaTotal.getItems().get(0)));

        TableColumn<ClienteTotal, Integer> colunaPedidos = (TableColumn<ClienteTotal, Integer>) tabelaTotal.getColumns().get(1);
        String pedidos = String.valueOf(colunaPedidos.getCellData(tabelaTotal.getItems().get(0)));

        TableColumn<ClienteTotal, BigDecimal> colunaVtotal = (TableColumn<ClienteTotal, BigDecimal>) tabelaTotal.getColumns().get(3);
        BigDecimal valorNumTot = colunaVtotal.getCellData(tabelaTotal.getItems().get(0));
        String valorTotal = String.format("%.2f", valorNumTot);

        TableColumn<ClienteTotal, BigDecimal> colunaRecebidoBrl = (TableColumn<ClienteTotal, BigDecimal>) tabelaTotal.getColumns().get(4);
        BigDecimal valorBrlNum = colunaRecebidoBrl.getCellData(tabelaTotal.getItems().get(0));
        String valorBrl =  String.format("%.2f", valorBrlNum);


        TableColumn<ClienteTotal, BigDecimal> colunaRecebidoPIX = (TableColumn<ClienteTotal, BigDecimal>) tabelaTotal.getColumns().get(5);
        BigDecimal valorPixNum = colunaRecebidoPIX.getCellData(tabelaTotal.getItems().get(0));
        String valorPix = String.format("%.2f", valorPixNum);

        TableColumn<ClienteTotal, BigDecimal> colunaRecebidoUYU = (TableColumn<ClienteTotal, BigDecimal>) tabelaTotal.getColumns().get(7);
        BigDecimal valorUyuNum = colunaRecebidoUYU.getCellData(tabelaTotal.getItems().get(0));
        String valorUyu = String.format("%.2f", valorUyuNum);

        TableColumn<ClienteTotal, BigDecimal> colunaRecebidoCartao = (TableColumn<ClienteTotal, BigDecimal>) tabelaTotal.getColumns().get(6);
        BigDecimal valorCartaoNum = colunaRecebidoCartao.getCellData(tabelaTotal.getItems().get(0));
        String valorCartao = String.format("%.2f", valorCartaoNum);



        try (PDDocument document = new PDDocument()) {
            float margin = 30;
            float yPosition = 0;
            float marginP = 20;
            int pageNumber = 1;

            PDPage page = new PDPage();

            for (Cliente cliente : list) {
                if (yPosition == 0) {

                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                        yPosition = page.getMediaBox().getHeight() - marginP;

                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText("Nome");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Status");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("BRL");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("UYU");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Pago com");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Entregue");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Pago");
                        contentStream.endText();
                    }
                    yPosition -= 20;
                }

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText(cliente.getNome());
                        contentStream.newLineAtOffset(85, 0);;
                        contentStream.showText("  " + cliente.getStatus().replace("Atendendo", "Não pediu"));
                        contentStream.newLineAtOffset(90, 0);
                        contentStream.showText(String.valueOf(cliente.getBrl()) + " R$");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText(String.valueOf(cliente.getUyu()) + " $");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText(cliente.getFormaPagamento());
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText(String.valueOf(cliente.getEntregue()).replace("true", "SIM").replace("false","NÃO"));
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText(String.valueOf(cliente.getPago()).replace("true", "SIM").replace("false","NÃO"));

                        contentStream.endText();

                        yPosition -= 20;

                        if (yPosition < marginP) {
                            yPosition = 0;
                            pageNumber++;

                    }

                }
            }
            yPosition -= 101;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                System.out.println(marginP);
                System.out.println(yPosition);
                if (yPosition < marginP) {
                    pageNumber++;
                    System.out.println("MPVA");
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    try (PDPageContentStream contentStream2 = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                        yPosition = page.getMediaBox().getHeight() - marginP;
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);

                        contentStream2.newLineAtOffset(margin, yPosition);
                        contentStream2.showText("Atendimentos");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("   Pedidos");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("Valor total");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("R$ Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("$ Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("PIX Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("Cartão");

                        contentStream2.setFont(PDType1Font.HELVETICA, 10);
                        contentStream2.newLineAtOffset(-510, -20);
                        contentStream2.showText(atendimentos + " atendimentos");
                        contentStream2.newLineAtOffset(85, 0);;
                        contentStream2.showText(pedidos + " pedido(s)");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorTotal + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorBrl + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorUyu + " $");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorPix + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorCartao + " R$");

                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream2.newLineAtOffset(-497, -40);
                        contentStream2.showText("| Data do fechamento: " + horaAtual + " | Funcionário: " + nomeFuncionario.getText() + " | Chave do peso do dia: " + valorPeso.getText() + " |");

                        contentStream2.setFont(PDType1Font.HELVETICA, 7);
                        contentStream2.newLineAtOffset(175, -20);
                        contentStream2.showText("developed by @beforg | https://github.com/Beforg");
                        contentStream2.endText();
                    }

                } else {

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(margin, yPosition+81);
                    contentStream.showText("Atendimentos");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("   Pedidos");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("Valor total");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("R$ Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("$ Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("PIX Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("Cartão");

                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(-510, -20);
                    contentStream.showText(atendimentos + " atendimento(s)");
                    contentStream.newLineAtOffset(85, 0);;
                    contentStream.showText(pedidos + " pedido(s)");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorTotal + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorBrl + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorUyu + " $");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorPix + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorCartao + " R$");

                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(-497, -40);
                    contentStream.showText("| Data do fechamento: " + horaAtual + " | Funcionário: " + nomeFuncionario.getText() + " | Chave do peso do dia: " + valorPeso.getText() + " |");
                    contentStream.setFont(PDType1Font.HELVETICA, 7);

                    contentStream.newLineAtOffset(175, -20);;
                    contentStream.showText("developed by @beforg | https://github.com/Beforg");

                    contentStream.endText();

                }
            }

            PDPage anotationPage = new PDPage(PDRectangle.A4);
            document.addPage(anotationPage);
            try {
                PDPageContentStream contentStream = new PDPageContentStream(document, anotationPage);
                float fontSize = 12;
                PDFont font = PDType1Font.COURIER_BOLD;
                contentStream.setFont(font, fontSize);
                String texto = anotacoes.getText();
                contentStream.beginText();
                float posX = 0, posY = 800;
                int contador = 0 ;
                contentStream.newLineAtOffset(20,posY);
                for (int i = 0; i < texto.length(); i++) {

                    contentStream.showText(String.valueOf(texto.charAt(i)).replace("\n",""));
                    contador++;
                    posX += 7.5;

                    if (posX > 577.5) {
                        contentStream.endText();
                        System.out.println("Entrou no if");
                        posY -= 15;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(20, posY);
                        posX = 0;
                    }

                    System.out.println(contador);
                    System.out.println(posX);


                }
                contentStream.endText();
                contentStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            float fontSize = 10;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float marginBottom = 10;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset((pageWidth / 2) - 20, marginBottom);
                contentStream.showText("Página " + pageNumber);
                contentStream.endText();
            }
            nomeFuncionario.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar PDF");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            String dataAtual = dateFormat.format(new Date());
            String nomeArquivo = "caixa_" + dataAtual + ".pdf";
            fileChooser.setInitialFileName(nomeArquivo);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage primaryStage = (Stage) botaoFinalizar.getScene().getWindow();
            File fileToSave = fileChooser.showSaveDialog(primaryStage);

            if (fileToSave != null) {
                try {
                    document.save(fileToSave.getAbsolutePath());
                    document.close();
                    System.out.println("Tabela de clientes salva em " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cancelado pelo usuário");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
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
        AdicionarControler.peso = valorPeso.getText();
        if (!verificaJanela) {
            FXMLLoader segundaTela = new FXMLLoader(getClass().getResource("adicionar.fxml"));
            Scene tela2 = new Scene(segundaTela.load());
            Stage stageAdicionar = new Stage();
            segundaTela.getController();
            stageAdicionar.setTitle("Adicionar pedido");
            stageAdicionar.setScene(tela2);
            stageAdicionar.show();
            Image icon = new Image("adicionar-usuario.png");
            stageAdicionar.getIcons().add(icon);
            stageAdicionar.setResizable(false);
            stageAdicionar.setOnCloseRequest(event -> {
                event.consume();
                logout(stageAdicionar);
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
        ObservableList<Cliente> removeCliente = FXCollections.observableArrayList();

        for (Cliente cliente : list){
            if (cliente.getRemover().isSelected())
            {
                removeCliente.add(cliente);
            }
        }
        if (removeCliente.isEmpty()) {
            Alert removerVazio = new Alert(Alert.AlertType.ERROR);
            removerVazio.setTitle("Erro");
            removerVazio.setHeaderText("Nenhum atendimento selecionado");
            Stage alertStage = (Stage) removerVazio.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("botao-x.png"));
            removerVazio.showAndWait();

        } else {
            Alert confirmaRemover = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaRemover.setTitle("Remover atendimento");
            confirmaRemover.setHeaderText("Confirmar");
            Stage alertStage = (Stage) confirmaRemover.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("aviso.png"));
            confirmaRemover.setContentText("Tem certeza que deseja remover os atendimentos selecionados? os atendimentos excluídos não poderão ser recuperados.");

            confirmaRemover.getButtonTypes().stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .ifPresent(buttonType -> {
                        Button button = (Button) confirmaRemover.getDialogPane().lookupButton(buttonType);
                        button.setDefaultButton(false);

                    });

            if (confirmaRemover.showAndWait().get() == ButtonType.OK) {
                list.removeAll(removeCliente);

            }
        }

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
        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();
        String oldName = cliente.getNome();
        System.out.println(oldName);
        cliente.setNome(nomeClienteTroca.getNewValue());
        if (cliente.getNome().equals("")) {
            cliente.setNome(oldName);
            tabelaCliente.refresh();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("Ocorreu um erro na aplicação");
            alert.setContentText("Preencha o nome para continuar");
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

    public void editarValor() {
        System.out.println("CLick");
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


}

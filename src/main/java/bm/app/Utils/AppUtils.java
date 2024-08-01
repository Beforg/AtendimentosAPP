package bm.app.Utils;

import bm.app.Infra.configuration.ConfigUtil;
import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.cliente.Cliente;
import bm.app.Model.cliente.ClientesTableView;
import bm.app.Model.credenciamento.AdminTableView;
import bm.app.Model.credenciamento.FuncionariosTableView;
import bm.app.Model.anotacoes.Notas;
import bm.app.Model.FormaPagamento;
import bm.app.Model.notas.NotasClienteTableView;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.util.converter.BigDecimalStringConverter;


import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AppUtils {

    public static void adicionaItensChoiceBox(ChoiceBox<String> choiceBoxFormaPagamento) {
       for (FormaPagamento formaPagamento : FormaPagamento.values()) {
           choiceBoxFormaPagamento.getItems().add(formaPagamento.getFormaPagamento());
       }
    }

    public static void mostraDetalhesPedido(TableView<PedidoTableView> tabelaPedidos, CheckBox pago, CheckBox entregue,
                                            Label codigo, Label dataHora, Label chavePeso) {
       tabelaPedidos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               Pedido pedido = new Pedido(newValue);
               LocalDate date = pedido.getDataPedido();
               System.out.println(pedido.getHoraPedido()+"  "+pedido.getDataPedido());
               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
               String df = date.format(dateFormatter);

                codigo.setText(pedido.getId().toString());
                dataHora.setText(df+", "+pedido.getHoraPedido());
                chavePeso.setText(pedido.getChavePeso());
                pago.setSelected(pedido.verificaPago());
                entregue.setSelected(pedido.verificaEntregue());
                pago.setDisable(false);
                codigo.setVisible(true);
                dataHora.setVisible(true);
                chavePeso.setVisible(true);
                entregue.setDisable(false);

           } else {
               codigo.setVisible(false);
               dataHora.setVisible(false);
               chavePeso.setVisible(false);
               pago.setDisable(true);
               entregue.setDisable(true);
           }
       });
    }
    public static void configuraTabelaPedidos(TableView<PedidoTableView> tabelaPedidos, TableColumn<PedidoTableView, String> nomeCliente,
                                              TableColumn<PedidoTableView, String> entregador, TableColumn<PedidoTableView, String> statusCliente,
                                              TableColumn<PedidoTableView, BigDecimal> brl, TableColumn<PedidoTableView, BigDecimal> uyu,
                                              TableColumn<PedidoTableView, CheckBox> deleta, TableColumn<PedidoTableView, String> pagamento,
                                              ObservableList<PedidoTableView> list, PedidoDAO pedidoDAO) {
        tabelaPedidos.setPlaceholder(new Label("Não há atendimentos registrados."));
        nomeCliente.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("nome"));
        nomeCliente.setCellFactory(TextFieldTableCell.forTableColumn());

        entregador.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("entregador"));
        entregador.setCellFactory(TextFieldTableCell.forTableColumn());
        entregador.setOnEditCommit(event -> {
            PedidoTableView pedidoTableView = event.getRowValue();
            pedidoTableView.setEntregador(event.getNewValue());
            Pedido pedidoDb = new Pedido(pedidoTableView);
            pedidoDAO.atualizarEntregador(pedidoDb);
        });

        statusCliente.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("status"));
        statusCliente.setCellFactory(ChoiceBoxTableCell.forTableColumn("Atendendo", "Pedido", "Entregue", "Não pago", "Pago"));
        statusCliente.setOnEditCommit(event -> {
            PedidoTableView pedidoTableView = event.getRowValue();
            pedidoTableView.setStatus(event.getNewValue());

            Pedido pedido = new Pedido(pedidoTableView);
            pedidoDAO.atualizarStatus(pedido);
            atualizarTabelas(list);
        });
        brl.setCellValueFactory(new PropertyValueFactory<PedidoTableView, BigDecimal>("brl"));
        brl.setCellFactory(column -> {
            FormataTabelaMonetaria brlCell = new FormataTabelaMonetaria(new Locale("pt", "BR"), "BRL");
            brlCell.setTabelaCliente(tabelaPedidos);
            return brlCell;
        });


        uyu.setCellValueFactory(new PropertyValueFactory<PedidoTableView, BigDecimal>("uyu"));
        uyu.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        uyu.setCellFactory(column -> {
            FormataTabelaMonetaria brlCell = new FormataTabelaMonetaria(new Locale("es", "UY"), "UYU");
            brlCell.setTabelaCliente(tabelaPedidos);
            return brlCell;
        });
        deleta.setCellValueFactory(new PropertyValueFactory<PedidoTableView, CheckBox>("remover"));
        pagamento.setCellValueFactory(new PropertyValueFactory<PedidoTableView, String>("formaPagamento"));
        pagamento.setCellFactory(ChoiceBoxTableCell.forTableColumn("Reais", "Cartão", "Pix", "Boleto", "Cheque","Pesos"));
        pagamento.setOnEditCommit(event -> {
            PedidoTableView pedidoTableView = event.getRowValue();
            pedidoTableView.setFormaPagamento(event.getNewValue());
            Pedido pedido = new Pedido(pedidoTableView);
            pedidoDAO.atualizarFormaPagamaneto(pedido);
            atualizarTabelas(list);
        });
        tabelaPedidos.setEditable(true);
        tabelaPedidos.setItems(list);

    }

    public static void configuraTabelaPedidosTotal(TableColumn<PedidoTotalTableView, Integer> clienteTotal,
                                                   TableColumn<PedidoTotalTableView, Integer> pedidosTotais,
                                                   TableColumn<PedidoTotalTableView, Integer> entreguesTot,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> valorTotal,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> brlRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> uyuRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> pixRecebido,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> recebidoCartao,
                                                   TableColumn<PedidoTotalTableView, BigDecimal> valor_npg,
                                                   TableView<PedidoTotalTableView> tabelaTotal,
                                                   ObservableList<PedidoTotalTableView> listPedidosTotal,
                                                   TableView<PedidoTableView> tabelaPedidos,
                                                   String valorTotalReais,
                                                   BigDecimal valorTotalPix,
                                                   BigDecimal valorTotalPesos,
                                                   BigDecimal valorTotalPedidos,
                                                   int atendTotal,
                                                   BigDecimal valorNrecebido,
                                                   IntegerProperty pedidosProperty,
                                                   IntegerProperty entreguesProperty,
                                                   BigDecimal valorCartao,
                                                   TableColumn<PedidoTableView, BigDecimal> brl) {
        clienteTotal.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("atendimentos"));

        pedidosTotais.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("pedidos"));
        entreguesTot.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, Integer>("entregues"));

        valorTotal.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorTotal"));
        valorTotal.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("pt", "BR")));

        brlRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorBrl"));
        brlRecebido.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("pt", "BR")));

        uyuRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorUyu"));
        uyuRecebido.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("es", "UY", "UYU")));

        pixRecebido.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorPix"));
        pixRecebido.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("pt", "BR")));

        recebidoCartao.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorPix"));
        recebidoCartao.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("pt", "BR")));

        valor_npg.setCellValueFactory(new PropertyValueFactory<PedidoTotalTableView, BigDecimal>("valorNaoRecebido"));
        valor_npg.setCellFactory(column -> new FormataTabelaMonetariaTotal(new Locale("pt", "BR")));
        AppUtils.vincularSoma(tabelaPedidos, brl,tabelaTotal,valorTotal);

        tabelaPedidos.getItems().addListener((ListChangeListener<? super PedidoTableView>) c -> {
            int pedidosCount = 0;
            int entregasCount = 0;
            for (PedidoTableView pedidoTableView : tabelaPedidos.getItems()) {
                if ("Pedido".equals(pedidoTableView.getStatus()) || "Entregue".equals(pedidoTableView.getStatus())
                        || "Não pago".equals(pedidoTableView.getStatus()) || "Pago".equals(pedidoTableView.getStatus())) {
                    pedidosCount++;
                }


            }
            for (PedidoTableView pedidoTableView : tabelaPedidos.getItems()) {
                if (pedidoTableView.isEntregue()) {
                    entregasCount++;
                }
            }
            entreguesProperty.set(entregasCount);
            pedidosProperty.set(pedidosCount);
        });
        PedidoTotalTableView total = new PedidoTotalTableView(atendTotal, pedidosProperty.get(), valorTotalPedidos, new BigDecimal(valorTotalReais), valorTotalPix, valorTotalPesos, valorNrecebido, valorCartao, entreguesProperty.get());
        total.atendimentosProperty().bind(Bindings.size(tabelaPedidos.getItems()));
        total.pedidosProperty().bind(pedidosProperty);
        total.entreguesProperty().bind(entreguesProperty);
        listPedidosTotal.add(total);

        tabelaTotal.getItems().addAll(listPedidosTotal);
    }
    public static void configuraTabelaNotasPendentes(TableColumn<NotasClienteTableView, String> nomeCliente,
                                                    TableColumn<NotasClienteTableView, String> dataPedido,
                                                    TableColumn<NotasClienteTableView, String> valorPedido,
                                                    TableColumn<NotasClienteTableView, String> chavePeso,
                                                    TableColumn<NotasClienteTableView, String> tcIdPedido,
                                                    TableView<NotasClienteTableView> tabelaNotas,
                                                    ObservableList<NotasClienteTableView> listNotas) {
        nomeCliente.setCellValueFactory(new PropertyValueFactory<NotasClienteTableView, String>("nomeCliente"));
        dataPedido.setCellValueFactory(new PropertyValueFactory<NotasClienteTableView, String>("data"));
        valorPedido.setCellValueFactory(new PropertyValueFactory<NotasClienteTableView, String>("valorPedido"));
        chavePeso.setCellValueFactory(new PropertyValueFactory<NotasClienteTableView, String>("chavePeso"));
        tcIdPedido.setCellValueFactory(new PropertyValueFactory<NotasClienteTableView, String>("idPedido"));
        tcIdPedido.setCellFactory(tc -> {
            TableCell<NotasClienteTableView, String> cellNotas = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(getItem());
                        setOnMouseClicked(event -> {
                            NotasClienteTableView notasClienteTableView = getTableView().getItems().get(getIndex());
                            Clipboard clipboard = Clipboard.getSystemClipboard();
                            ClipboardContent content = new ClipboardContent();
                            content.putString(notasClienteTableView.getIdPedido());
                            clipboard.setContent(content);
                        });
                    }
                }
            };
            return cellNotas;
        });
        tabelaNotas.setItems(listNotas);
    }
    public static void configuraTabelaFuncionarios(TableColumn<FuncionariosTableView, String> tcId,
                                                   TableColumn<FuncionariosTableView, String> tcNome) {
        tcId.setCellValueFactory(new PropertyValueFactory<FuncionariosTableView, String>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<FuncionariosTableView, String>("nome"));
    }
    public static void configuraTabelaClientes(TableColumn<ClientesTableView, String> tcId,
                                               TableColumn<ClientesTableView, String> tcNome,
                                               TableColumn<ClientesTableView, String> tcTelefone,
                                               TableColumn<ClientesTableView, String> tcEndereco,
                                               String type) {
        tcId.setCellValueFactory(new PropertyValueFactory<ClientesTableView, String>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<ClientesTableView, String>("nome"));
        if (type.equals("Max")) {
            tcTelefone.setCellValueFactory(new PropertyValueFactory<ClientesTableView, String>("telefone"));
            tcEndereco.setCellValueFactory(new PropertyValueFactory<ClientesTableView, String>("endereco"));
        }

    }
    public static void configuraTabelaAdmin(TableView<AdminTableView> tabelaAdmin,
                                           TableColumn<AdminTableView, String> tcId,
                                           TableColumn<AdminTableView, String> tcNome) {
        tcId.setCellValueFactory(new PropertyValueFactory<AdminTableView, String>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<AdminTableView, String>("nome"));
    }
    public static void limpaCamposAdicionarPedido(TextField nome, TextField valor, ChoiceBox<String> formaPagamento, CheckBox pedido,
                                                  Label nomeAdd,
                                                  ComboBox<Cliente> comboBox) {
        if (comboBox.getValue() == null) {
            nomeAdd.setText("Atendimento de " + nome.getText() + " adicionado!");
            nomeAdd.setVisible(true);
        } else {
            nomeAdd.setText("Atendimento de " + comboBox.getValue().getNome() + " adicionado!");
            nomeAdd.setVisible(true);
            comboBox.setValue(null);
        }
        valor.setDisable(true);
        formaPagamento.setDisable(true);
        nome.clear();
        valor.clear();
        formaPagamento.setValue(null);
        pedido.setSelected(false);

    }

    public static void liberaCamposAdicionarPedido(CheckBox adicionaPedido, TextField valorPedido, ChoiceBox<String> listaPagamento) {
        if (adicionaPedido.isSelected()) {
            valorPedido.setDisable(false);
            valorPedido.setText("0,00");
            listaPagamento.setDisable(false);
        } else {
            valorPedido.setDisable(true);
            valorPedido.setText("");
            listaPagamento.setDisable(true);
        }
    }
    public static void atualizarTabelas(ObservableList<PedidoTableView> list) {
        PedidoTableView novoPedidoTableView = new PedidoTableView();
        list.add(novoPedidoTableView);
        list.remove(novoPedidoTableView);
    }

    public static PedidoTableView editarNome(TableView<PedidoTableView> tabelaCliente, TableColumn.CellEditEvent<PedidoTableView, String> nomeClienteTroca) {
        PedidoTableView pedidoTableView = tabelaCliente.getSelectionModel().getSelectedItem();
        String oldName = pedidoTableView.getNome();
        pedidoTableView.setNome(nomeClienteTroca.getNewValue());
        if (pedidoTableView.getNome().equals("")) {
            pedidoTableView.setNome(oldName);
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
        return pedidoTableView;
    }
    public static void hoverImagem(ImageView imageView, String path) {
        URL url = AppUtils.class.getResource(path);
        if (url != null) {
            Image image = new Image(url.toExternalForm());
            imageView.setImage(image);
        } else {
            System.err.println("URL Não encontrada: " + path);
        }
    }
    public static void configuraLista(ListView<Notas> lista, ObservableList<Notas> notasList) {
        lista.setPlaceholder(new Label("Nenhuma nota disponível"));
        lista.setItems(notasList);
        lista.setCellFactory(param -> new ListCell<Notas>() {
            @Override
            protected void updateItem(Notas item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Tooltip tooltip;
                    if (item.getDataEdicao() == null) {
                        tooltip = new Tooltip("Criado: "+ item.getDataCriacao());
                    } else {
                        tooltip = new Tooltip("Criado: "+ item.getDataCriacao()+"\nEditado: " + item.getDataEdicao());
                    }


                    Label label = new Label("Título: " + item.getTitulo() + "\nDescrição: " + item.getDescricao() );
                    label.setStyle("-fx-font-size: 14px; -fx-font-family: 'Yu Gothic UI'; -fx-text-fill: white;");
                    label.setTooltip(tooltip);
                    label.setWrapText(true);
                    label.setMaxWidth(getListView().getWidth() - 20);
                    setGraphic(label);
                }
            }
        });
    }

    public static void adicionarNotasView(Pane pane, ListView<Notas> lista, boolean p, boolean l, TextField titulo) {
        pane.setVisible(p);
        lista.setVisible(l);
        titulo.requestFocus();
    }
    public static void limpaCamposNotas(TextField titulo, TextArea texto) {
        titulo.clear();
        texto.clear();
    }

    public static void editarNotasView(Pane paneAdicionarTarefas, ListView<Notas> listaDeNotas, TextField tfTituloNota,
                                       TextArea anotacoes, Button btEditarNota) {
        Notas nota = listaDeNotas.getSelectionModel().getSelectedItem();
        if (nota == null) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao editar nota", "Selecione uma nota para editar","botao-x.png");
            return;
        }
        tfTituloNota.setText(nota.getTitulo());
        anotacoes.setText(nota.getDescricao());
        adicionarNotasView(paneAdicionarTarefas, listaDeNotas, true, false, tfTituloNota);
        btEditarNota.setVisible(true);
    }

    public static void limparCamposCadastroCredenciamento(PasswordField passwordField, TextField ... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
        }
        passwordField.clear();
    }
    public static void vincularSoma(TableView<PedidoTableView> tabelaCliente,
                                    TableColumn<PedidoTableView, BigDecimal> brlColumn,
                                    TableView<PedidoTotalTableView> tabelaTotal,
                                    TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaCliente.getItems().stream()
                                .map(PedidoTableView::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaCliente.getItems());
        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularFalta(TableView<PedidoTableView> tabelaAtendimento,
                                     TableView<PedidoTotalTableView> tabelaTotal,
                                     TableColumn<PedidoTotalTableView,
                                             BigDecimal> valorTotalColumn) {

        DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                        tabelaAtendimento.getItems().stream()
                                .filter(atendimento -> !atendimento.isPago())
                                .map(PedidoTableView::getBrl)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum(),
                tabelaAtendimento.getItems());

        valorTotalColumn.setCellValueFactory(data -> {
            BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
            return Bindings.createObjectBinding(() -> valorTotal);
        });

        tabelaAtendimento.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularDinheiro(
            TableView<PedidoTableView> tabelaCliente,
            TableView<PedidoTotalTableView> tabelaTotal,
            TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn,
            String tipoPagamento,
            boolean peso
    ) {
        ObservableList<PedidoTableView> pedidosList = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            pedidosList.clear();
            for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
                String pagamento = pedidoTableView.getFormaPagamento();
                Boolean pago = pedidoTableView.isPago();

                if (tipoPagamento.equals(pagamento) && pago != null && pago) {
                    pedidosList.add(pedidoTableView);
                }
            }
        });

        pedidosList.addListener((ListChangeListener<PedidoTableView>) change -> {
            if (peso) {
                DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                                pedidosList.stream()
                                        .map(PedidoTableView::getUyu)
                                        .mapToDouble(BigDecimal::doubleValue)
                                        .sum(),
                        pedidosList

                );

                BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
                valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
                tabelaTotal.refresh();

            } else {
                DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                                pedidosList.stream()
                                        .map(PedidoTableView::getBrl)
                                        .mapToDouble(BigDecimal::doubleValue)
                                        .sum(),
                        pedidosList

                );

                BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
                valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
                tabelaTotal.refresh();
            }
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }
    public static void vincularCartao(
            TableView<PedidoTableView> tabelaCliente,
            TableView<PedidoTotalTableView> tabelaTotal,
            TableColumn<PedidoTotalTableView, BigDecimal> valorTotalColumn,
            boolean peso
    ) {
        ObservableList<PedidoTableView> pedidosList = FXCollections.observableArrayList();

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            pedidosList.clear();
            for (PedidoTableView pedidoTableView : tabelaCliente.getItems()) {
                String pagamento = pedidoTableView.getFormaPagamento();
                Boolean pago = pedidoTableView.isPago();

                if ("Cartão".equals(pagamento)  && pago != null && pago) {
                    pedidosList.add(pedidoTableView);
                } else if ("Boleto".equals(pagamento) && pago != null && pago) {
                    pedidosList.add(pedidoTableView);
                } else if ("Cheque".equals(pagamento) && pago != null && pago) {
                    pedidosList.add(pedidoTableView);
                }
            }
        });

        pedidosList.addListener((ListChangeListener<PedidoTableView>) change -> {
            if (peso) {
                DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                                pedidosList.stream()
                                        .map(PedidoTableView::getUyu)
                                        .mapToDouble(BigDecimal::doubleValue)
                                        .sum(),
                        pedidosList

                );

                BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
                valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
                tabelaTotal.refresh();

            } else {
                DoubleBinding somaBinding = Bindings.createDoubleBinding(() ->
                                pedidosList.stream()
                                        .map(PedidoTableView::getBrl)
                                        .mapToDouble(BigDecimal::doubleValue)
                                        .sum(),
                        pedidosList

                );

                BigDecimal valorTotal = BigDecimal.valueOf(somaBinding.get());
                valorTotalColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> valorTotal));
                tabelaTotal.refresh();
            }
        });

        tabelaCliente.getItems().addListener((ListChangeListener<PedidoTableView>) change -> {
            tabelaTotal.refresh();
        });
    }

    public static void abrirEdicaoCliente(TableView<ClientesTableView> tabelaClientes, TabPane tabPane, Tab tab,
                                          String type, ClientesTableView cliente, TextField tfRua,
                                          TextField tfNumero, TextField tfBairro, TextField tfComplemento,
                                          TextField tfNome, TextField tfTelefone) {
        boolean val = Validacao.verificaClienteSelecionado(tabelaClientes);
        if (!val) {
            CaixaDeMensagem.mensagemErro("Erro", "Cliente não selecionado", "Selecione um cliente para editar","botao-x.png");
            return;
        }
        if (type.equals("Fechar")) {
            tabPane.getTabs().remove(tab);
        } else if (type.equals("Editar")) {
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            tfNome.setText(cliente.getNome());
            tfRua.setText(cliente.getEndereco());
            tfTelefone.setText(cliente.getTelefone());
        }
    }

    public static String enderecoBuilder(String rua, String numero, String bairro, String complemento) {
        return rua + "/" + "Num." + numero + "/" + bairro + "/" + "Comp.:" + complemento + "/";
    }

    public static void ocultaTabs(TabPane tabPane, Tab tab) {
        tabPane.getTabs().remove(tab);
    }

    public static void listenerTabelaClientes(TableView<ClientesTableView> tabela,Label nome, Label labelEndereco, Label labelTelefone) {
        tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                labelEndereco.setText(newValue.getEndereco().replace("/", " | "));
                labelTelefone.setText(newValue.getTelefone());
                nome.setText(newValue.getNome());
            } else {
                labelEndereco.setText("");
                labelTelefone.setText("");
                nome.setText("");
            }
        });
    }
    public static void showInputsAdicionar(ComboBox<Cliente> cb, TextField tf, boolean vi) {
        if (cb.isVisible()) {
            cb.setVisible(false);
            tf.setVisible(true);
        } else {
            cb.setVisible(true);
            tf.setVisible(false);
        }
    }
    public static void tabResultadoBuscaPedido(TabPane tabPane, Tab tab,Tab tabBusca, Pedido pedido, String type) {
        if (type.equals("Abrir")) {
            tabPane.getTabs().add(tab);
            tab.setText("Pedido: " + pedido.getNome() + " - " + pedido.getDataPedido());
            tabPane.getSelectionModel().select(tab);
        } else if (type.equals("Fechar")) {
            tabPane.getTabs().remove(tab);
            tabPane.getSelectionModel().select(tabBusca);
        }
    }
    public static void codigoToClipboard(Label labelCodigoPedido) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(labelCodigoPedido.getText());
        clipboard.setContent(content);

        Tooltip tooltip = new Tooltip("Código copiado para a área de transferência!");
        tooltip.setAutoHide(true);
        tooltip.show(labelCodigoPedido.getScene().getWindow(),
                labelCodigoPedido.localToScreen(labelCodigoPedido.getBoundsInLocal()).getMinX(),
                labelCodigoPedido.localToScreen(labelCodigoPedido.getBoundsInLocal()).getMinY() - tooltip.getHeight() - 5);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> tooltip.hide());
                    }
                },
                2000
        );
    }

    public static void limpaCamposCadastrarCliente(TextField ... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
        }
    }
    public static void trocaTelaLogin(Pane paneInicio, Pane paneBanco, boolean i, boolean b, TextField url, TextField user, PasswordField senha) {
        paneInicio.setVisible(i);
        paneBanco.setVisible(b);
        if (b) {
            url.setText(ConfigUtil.getProperty("urlBanco"));
            user.setText(ConfigUtil.getProperty("user"));
            senha.setText(ConfigUtil.getProperty("senha"));
        } else {
            url.clear();
            user.clear();
            senha.clear();
        }
    }
}

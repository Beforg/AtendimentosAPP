package bm.app.Utils;

import bm.app.Infra.dao.PedidoDAO;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Model.notas.Notas;
import bm.app.Model.FormaPagamento;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.net.URL;
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
                codigo.setText(pedido.getId().toString());
                dataHora.setText(pedido.getDataPedido().toString());
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
        ValorTotal.vincularSoma(tabelaPedidos, brl,tabelaTotal,valorTotal);

        tabelaPedidos.getItems().addListener((ListChangeListener<? super PedidoTableView>) c -> {
            int pedidosCount = 0;
            int entregasCount = 0;
            for (PedidoTableView pedidoTableView : tabelaPedidos.getItems()) {
                if ("Pedido".equals(pedidoTableView.getStatus()) || "Entregue".equals(pedidoTableView.getStatus())
                        || "Não Pago".equals(pedidoTableView.getStatus()) || "Pago".equals(pedidoTableView.getStatus())) {
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
    public static void limpaCamposAdicionarPedido(TextField nome, TextField valor, ChoiceBox<String> formaPagamento, CheckBox pedido) {
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
                    Label label = new Label("Título: " + item.getTitulo() + "\nDescrição: " + item.getDescricao() );
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
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao editar nota", "Selecione uma nota para editar");
            return;
        }
        tfTituloNota.setText(nota.getTitulo());
        anotacoes.setText(nota.getDescricao());
        adicionarNotasView(paneAdicionarTarefas, listaDeNotas, true, false, tfTituloNota);
        btEditarNota.setVisible(true);
    }
    public static void mostrarEdicaoFuncionario(Label labelNome, Label labelUser, Button excluir, Button editar,
                                                Button salvar, TextField nome, TextField user, TextField senha,
                                                boolean label, boolean buttonFirst, boolean buttonSecond, boolean textFields,
                                                Credenciamento credenciamento) {

        labelNome.setVisible(label);
        labelUser.setVisible(label);
        excluir.setVisible(buttonFirst);
        editar.setVisible(buttonFirst);
        salvar.setVisible(buttonSecond);
        nome.setVisible(textFields);
        user.setVisible(textFields);
        senha.setVisible(textFields);
        if (credenciamento != null) {
            nome.setText(credenciamento.getNome());
            user.setText(credenciamento.getUsername());

        }

    }
}

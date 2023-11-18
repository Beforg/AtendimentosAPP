package bm.app.Controller;

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
    private CheckBox adicionaPedido;
    @FXML
    private TextField valorPedido;
    @FXML
    private ChoiceBox<String> listaPagamento;
    @FXML
    private TextField chave_peso;
    @FXML
    private CheckBox continuaAdd;
    @FXML
    private Label nomeAdd;
    String pagamentoSelecionado;
    String[] pagamentos = {"Cartão","Pesos","Reais","PIX","Outro"};

    public static String peso;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPagamento.getItems().addAll(pagamentos);
        chave_peso.setText(peso);
        listaPagamento.setValue("Reais");
    }


    public void cancelar() throws IOException {
        Alert fechar = new Alert(Alert.AlertType.CONFIRMATION);
        fechar.setTitle("Cancelar atendimento");
        fechar.setContentText("Tem certeza que deseja cancelar?");
        Stage alertStage = (Stage) fechar.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("aviso.png"));

        if (fechar.showAndWait().get() == ButtonType.OK) {
            HelloController.verificaJanela = false;
            Stage stage = (Stage) cancelarBot.getScene().getWindow();
            stage.close();
        }
    }
    public void adicionar() {
        if (adicionaNome.getText().equals("")){

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
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("botao-x.png"));
            alert.showAndWait();
        } else if (!adicionaNome.getText().equals("") && adicionaPedido.isSelected() && valorPedido.getText().equals("") || valorPedido.getText().matches(".*[a-zA-Z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("Ocorreu um erro na aplicação");
            alert.setContentText("Valor inválido");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("botao-x.png"));
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
        else {
            pagamentoSelecionado = listaPagamento.getValue();
            if (pagamentoSelecionado == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Atenção");
                alert.setHeaderText("Ocorreu um erro na aplicação");
                alert.setContentText("Selecione a forma de pagamento");
                alert.getButtonTypes().stream()
                        .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                        .findFirst()
                        .ifPresent(buttonType -> {
                            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
                            button.setDefaultButton(false);

                        });
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-font-size: 14px; -fx-font-family: Arial, sans-serif;");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("botao-x.png"));
                alert.showAndWait();
            } else {
                if (adicionaPedido.isSelected()){
                    if (pagamentoSelecionado == null) {
                        System.out.println("Erro");
                    }

                    HelloController.adicionarAtendimento(adicionaNome.getText(), "Pedido", new BigDecimal(valorPedido.getText().replace(",",".")), new BigDecimal(valorPedido.getText().replace(",",".")).multiply(new BigDecimal(chave_peso.getText().replace(",","."))), pagamentoSelecionado, false, false,"");
                    if (continuaAdd.isSelected()) {
                        nomeAdd.setText(adicionaNome.getText() + " adicionado");
                        adicionaNome.setText("");
                        adicionaPedido.setSelected(true);
                        valorPedido.setText("");
                        listaPagamento.setValue("Reais");
                        continuaAdd.setSelected(true);
                        nomeAdd.setVisible(true);
                    } else {
                        Stage stage = (Stage) cancelarBot.getScene().getWindow();
                        stage.close();
                    }



                } else {
                    HelloController.adicionarAtendimento(adicionaNome.getText(), "Atendendo", new BigDecimal("0"), new BigDecimal("0"), pagamentoSelecionado, false, false,"");
                    if (continuaAdd.isSelected()) {
                        nomeAdd.setText(adicionaNome.getText() + " adicionado com sucesso");
                        adicionaNome.setText("");
                        valorPedido.setText("");
                        listaPagamento.setValue("Reais");
                        continuaAdd.setSelected(true);
                        nomeAdd.setVisible(true);
                    } else {
                        Stage stage = (Stage) cancelarBot.getScene().getWindow();
                        stage.close();
                    }
                }
            }

        }

    }

    public void pedidoMarcado() {
        if (adicionaPedido.isSelected()) {
            valorPedido.setDisable(false);
            valorPedido.setText("0,00");
        } else {
            valorPedido.setDisable(true);
            valorPedido.setText("");
        }

    }


}

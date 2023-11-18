package bm.app.View;

import bm.app.Controller.HelloController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Atendimentos");
        Parent fxmlPrincipal = FXMLLoader.load(getClass().getResource("tela.fxml"));
        Scene principal = new Scene(fxmlPrincipal);
        stage.setResizable(false);


        principal.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!HelloController.verificaJanela) {
                        FXMLLoader segundaTela = new FXMLLoader(getClass().getResource("adicionar.fxml"));
                        Scene tela2 = null;
                        try {
                            tela2 = new Scene(segundaTela.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stageAdicionar = new Stage();
                        segundaTela.getController();
                        stageAdicionar.setTitle("Adicionar pedido");
                        stageAdicionar.setScene(tela2);
                        stageAdicionar.show();
                        stageAdicionar.setResizable(false);
                        Image icon = new Image("adicionar-usuario.png");
                        stageAdicionar.getIcons().add(icon);
                        stageAdicionar.setOnCloseRequest(eventJanela -> {
                            event.consume();
                            logout(stageAdicionar);
                            HelloController.verificaJanela = false;
                        });
                        HelloController.verificaJanela = true;
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
            }
        });
        stage.setScene(principal);
        stage.show();
        Image icon = new Image("fone-de-ouvido.png");
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(event -> {
        event.consume();
        logout(stage);

    });

    }
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText("Tem certeza que quer sair?");
        alert.setContentText("O progresso não salvo será perdido.");
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


    public static void main(String[] args) {
        launch();
    }
}
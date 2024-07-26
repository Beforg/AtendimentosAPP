package bm.app.View;

import bm.app.Controller.AppController;
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
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Credenciamento");
        stage.initStyle(StageStyle.UNDECORATED);
        Parent fxmlPrincipal = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene principal = new Scene(fxmlPrincipal);
        stage.setResizable(false);



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
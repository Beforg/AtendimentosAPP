package bm.app.View;

import bm.app.Controller.AdicionarControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewService {
    public Stage ajudaView() throws IOException {
        Stage stageAjuda = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ajudaTela.fxml"));
        Parent content = loader.load();
        Scene ajudaView = new Scene(content);
        loader.getController();
        stageAjuda.setTitle("Ajuda");
        stageAjuda.setScene(ajudaView);
        Image icon = new Image("ajuda.png");
        stageAjuda.getIcons().add(icon);
        stageAjuda.setResizable(false);

        return stageAjuda;
    }
    public Stage sobreView() throws IOException {
        Stage stageSobre = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sobre.fxml"));
        Parent content = loader.load();
        Scene sobreView = new Scene(content);
        loader.getController();
        stageSobre.setTitle("Sobre");
        stageSobre.setScene(sobreView);
        Image icon = new Image("sobre-nos.png");
        stageSobre.getIcons().add(icon);
        stageSobre.setResizable(false);

        return stageSobre;
    }

    public static Stage telaAdicionar(boolean verificaJanela, TextField valorPeso) throws IOException {
        Stage stageAdicionar = new Stage();
        if (!verificaJanela) {
            AdicionarControler.peso = valorPeso.getText();
            FXMLLoader segundaTela = new FXMLLoader();
            segundaTela.setLocation(ViewService.class.getResource("adicionar.fxml"));
            Parent content = segundaTela.load();
            Scene tela2 = new Scene(content);
            segundaTela.getController();
            stageAdicionar.setTitle("Adicionar pedido");
            stageAdicionar.setScene(tela2);
            stageAdicionar.show();
            Image icon = new Image("adicionar-usuario.png");
            stageAdicionar.getIcons().add(icon);
            stageAdicionar.setResizable(false);
        }
        return stageAdicionar;
    }

    public static void logout(Stage stage) {
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
}

package bm.app.Utils;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.awt.*;

public class CaixaDeMensagem {

    private static final String style = "-fx-font-size: 14px; -fx-font-family: 'Yu Gothic Light'";

    public static void mensagemErro(String titulo, String cabecalho, String conteudo, String path) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert(titulo, cabecalho, conteudo, alert, path);
        alert.showAndWait();
    }

    public static void mensagemInformacao(String titulo, String cabecalho, String conteudo, String path) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert(titulo, cabecalho, conteudo, alert, path);
        alert.showAndWait();
    }

    public static void mensagemAviso(String titulo, String cabecalho, String conteudo, String path) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert(titulo, cabecalho, conteudo, alert, path);
        alert.showAndWait();
    }

    public static boolean mensagemConfirmacao(String titulo, String cabecalho, String conteudo, String path) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert(titulo, cabecalho, conteudo, alert,path);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private static void alert(String titulo, String cabecalho, String conteudo, Alert alert, String path) {
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(path));
        alert.getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst()
                .ifPresent(buttonType -> {
                    Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
                    button.setDefaultButton(false);

                });
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(style);
    }
}

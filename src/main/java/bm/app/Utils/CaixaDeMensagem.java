package bm.app.Utils;

import bm.app.Controller.AppController;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CaixaDeMensagem {

    private static final String style = "-fx-font-size: 14px; -fx-font-family: 'Yu Gothic Light'";

    public static void mensagemErro(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert(titulo, cabecalho, conteudo, alert);
        alert.showAndWait();
    }

    public static boolean mensagemConfirmacao(String titulo, String cabecalho, String conteudo, Button cancelarBot) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert(titulo, cabecalho, conteudo, alert);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private static void alert(String titulo, String cabecalho, String conteudo, Alert alert) {
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
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

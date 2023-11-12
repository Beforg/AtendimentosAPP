package bm.app.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class RadioButtons {
    public static void anotacoesView(TextArea textArea, ImageView imageView, Button button, Label label) {
        button.setVisible(false);
        textArea.setVisible(true);
        imageView.setVisible(false);
        label.setVisible(false);
    }

    public static void promoView(TextArea textArea, ImageView imageView, Button button, Label label) {
        button.setVisible(true);
        textArea.setVisible(false);
        imageView.setVisible(true);
        label.setVisible(true);
    }
    public static void loadImage(ImageView imageView) {
        FileChooser path = new FileChooser();
        path.setTitle("Selecione uma imagem");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.jpg", "*.png", "*.jpeg", "*.gif");
        path.getExtensionFilters().add(extFilter);

        File arquivoSelecionado = path.showOpenDialog(null);
        if (arquivoSelecionado != null) {
            try {

                Image imagem = new Image(arquivoSelecionado.toURI().toString());
                imageView.setImage(imagem);
                imageView.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                imageView.setFitHeight(360);
                imageView.setFitWidth(258);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao carregar a imagem");
                alert.setContentText("Ocorreu um erro ao carregar a imagem: " + e.getMessage());
                alert.showAndWait();
            }
        }

    }
}

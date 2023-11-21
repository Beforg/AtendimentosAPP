package bm.app.View;

import bm.app.Controller.AdicionarControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class AdicionarView {
    public Stage telaAdicionar(boolean verificaJanela, TextField valorPeso) throws IOException {
        Stage stageAdicionar = new Stage();
        if (!verificaJanela) {
            AdicionarControler.peso = valorPeso.getText();
            FXMLLoader segundaTela = new FXMLLoader();
            segundaTela.setLocation(getClass().getResource("adicionar.fxml"));
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
}

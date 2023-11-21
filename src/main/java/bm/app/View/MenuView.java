package bm.app.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView {
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
        loader.setLocation(getClass().getResource("telaSobre.fxml"));
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
}

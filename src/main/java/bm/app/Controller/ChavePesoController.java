package bm.app.Controller;

import bm.app.Infra.configuration.ConfigUtil;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.View.ViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ChavePesoController implements Initializable {
    @FXML
    private TextField valorChave;


    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) valorChave.getScene().getWindow();
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
        if (valorChave.getText().isEmpty()) {
            CaixaDeMensagem.mensagemErro("Erro", "Erro ao salvar chave", "Por favor, preencha o campo chave.","botao-x.png");
            return;
        }
        ConfigUtil.setProperty("chavePeso", valorChave.getText());
        Stage stage = (Stage) valorChave.getScene().getWindow();
        stage.close();
    }

    private void carregarChavePeso() {
        valorChave.setText(ConfigUtil.getProperty("chavePeso"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarChavePeso();
    }
}

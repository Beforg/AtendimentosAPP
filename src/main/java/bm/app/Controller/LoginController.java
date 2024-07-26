package bm.app.Controller;

import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.View.ViewService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private PasswordField pfSenha;
    @FXML
    private TextField tfLogin;

    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();

    public void entrar() throws IOException {
        if (credenciamentoDAO.verificaLogin(pfSenha.getText(),tfLogin.getText())) {
            AppController.credenciamento = credenciamentoDAO.carregarCredenciamento(tfLogin.getText());
            ViewService.telaView();
            sair();
        }
    }

    public void sair() {
        Stage stage = (Stage) tfLogin.getScene().getWindow();
        stage.close();
    }
}

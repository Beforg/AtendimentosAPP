package bm.app.Controller;

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

    public void entrar() throws IOException {
        if (tfLogin.getText().equals("admin") && pfSenha.getText().equals("admin")) {
            System.out.println("Login efetuado com sucesso!");
            ViewService.telaView();
            sair();
        } else {
            System.out.println("Login ou senha inválidos!");
        }
    }

    public void sair() {
        Stage stage = (Stage) tfLogin.getScene().getWindow();
        stage.close();
    }
}

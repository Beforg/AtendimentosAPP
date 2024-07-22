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
    public static Stage funcionarioView() throws IOException {
        Stage stageFuncionario = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("funcionarios.fxml"));
        Parent content = loader.load();
        Scene funcionarioView = new Scene(content);
        loader.getController();
        stageFuncionario.setTitle("Funcionário");
        stageFuncionario.setScene(funcionarioView);
        //Image icon = new Image("funcionario.png");
        //stageFuncionario.getIcons().add(icon);
        stageFuncionario.setResizable(false);
        stageFuncionario.show();
        return stageFuncionario;
    }
    public static Stage clientesView() throws IOException {
        Stage stageClientes = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("clientes.fxml"));
        Parent content = loader.load();
        Scene clientesView = new Scene(content);
        loader.getController();
        stageClientes.setTitle("Clientes");
        stageClientes.setScene(clientesView);
        //Image icon = new Image("clientes.png");
        //stageClientes.getIcons().add(icon);
        stageClientes.setResizable(false);
        stageClientes.show();

        return stageClientes;
    }
    public static Stage administradorView() throws IOException {
        Stage stageAdministrador = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("adm.fxml"));
        Parent content = loader.load();
        Scene administradorView = new Scene(content);
        loader.getController();
        stageAdministrador.setTitle("Administrador");
        stageAdministrador.setScene(administradorView);
       // Image icon = new Image("administrador.png");
       // stageAdministrador.getIcons().add(icon);
        stageAdministrador.setResizable(false);
        stageAdministrador.show();

        return stageAdministrador;
    }
    public static Stage notasView() throws IOException {
        Stage stageNotas = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("notas.fxml"));
        Parent content = loader.load();
        Scene notasView = new Scene(content);
        loader.getController();
        stageNotas.setTitle("Notas");
        stageNotas.setScene(notasView);
        //Image icon = new Image("notas.png");
       // stageNotas.getIcons().add(icon);
        stageNotas.setResizable(false);

        return stageNotas;
    }
    public static Stage telaView() throws IOException {
        Stage stageTela = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("tela.fxml"));
        Parent content = loader.load();
        Scene telaView = new Scene(content);
        loader.getController();
        stageTela.setTitle("Tela");
        stageTela.setScene(telaView);
       // Image icon = new Image("tela.png");
       // stageTela.getIcons().add(icon);
        stageTela.setResizable(false);
        stageTela.show();
        return stageTela;
    }
    public static Stage clienteCadastroView() throws IOException {
        Stage stageClienteCadastro = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("cadastro-cliente.fxml"));
        Parent content = loader.load();
        Scene clienteCadastroView = new Scene(content);
        loader.getController();
        stageClienteCadastro.setTitle("Cadastro de cliente");
        stageClienteCadastro.setScene(clienteCadastroView);
       // Image icon = new Image("cliente-cadastro.png");
        //stageClienteCadastro.getIcons().add(icon);
        stageClienteCadastro.setResizable(false);
        stageClienteCadastro.show();
        return stageClienteCadastro;
    }
    public static Stage ajudaView() throws IOException {
        Stage stageAjuda = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("ajudaTela.fxml"));
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
    public static Stage sobreView() throws IOException {
        Stage stageSobre = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("sobre.fxml"));
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

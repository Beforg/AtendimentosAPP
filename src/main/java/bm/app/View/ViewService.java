package bm.app.View;

import bm.app.Controller.AdicionarControler;
import bm.app.Controller.AppController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class ViewService {
    public static Stage funcionarioView() throws IOException {
        Stage stageFuncionario = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("funcionarios.fxml"));
        Parent content = loader.load();
        stageFuncionario.initModality(Modality.APPLICATION_MODAL);
        Scene funcionarioView = new Scene(content);
        loader.getController();
        stageFuncionario.setTitle("Funcionário");
        stageFuncionario.setScene(funcionarioView);
        Image icon = new Image(Objects.requireNonNull(ViewService.class.getResourceAsStream("/ui/funcionario.png")));
        stageFuncionario.getIcons().add(icon);
        stageFuncionario.setResizable(false);
        stageFuncionario.showAndWait();
        return stageFuncionario;
    }
    public static Stage clientesView() throws IOException {
        Stage stageClientes = new Stage();
        FXMLLoader loader = new FXMLLoader();
        stageClientes.initModality(Modality.APPLICATION_MODAL);
        loader.setLocation(ViewService.class.getResource("clientes.fxml"));
        Parent content = loader.load();
        Scene clientesView = new Scene(content);
        loader.getController();
        stageClientes.setTitle("Clientes");
        stageClientes.setScene(clientesView);
        Image icon = new Image(Objects.requireNonNull(ViewService.class.getResourceAsStream("/ui/client.png")));
        stageClientes.getIcons().add(icon);
        stageClientes.setResizable(false);
        stageClientes.showAndWait();

        return stageClientes;
    }
    public static Stage administradorView() throws IOException {
        Stage stageAdministrador = new Stage();
        FXMLLoader loader = new FXMLLoader();
        stageAdministrador.initModality(Modality.APPLICATION_MODAL);
        loader.setLocation(ViewService.class.getResource("adm.fxml"));
        Parent content = loader.load();
        Scene administradorView = new Scene(content);
        loader.getController();
        stageAdministrador.setTitle("Administrador");
        stageAdministrador.setScene(administradorView);
        Image icon = new Image(Objects.requireNonNull(ViewService.class.getResourceAsStream("/ui/admin.png")));
        stageAdministrador.getIcons().add(icon);
        stageAdministrador.setResizable(false);
        stageAdministrador.showAndWait();

        return stageAdministrador;
    }
    public static Stage telaView() throws IOException {
        Stage stageTela = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("tela.fxml"));
        Parent content = loader.load();
        Scene telaView = new Scene(content);
        loader.getController();
        stageTela.setTitle("AtendimentosApp");
        stageTela.setScene(telaView);
         Image icon = new Image("fone-de-ouvido.png");
        stageTela.getIcons().add(icon);
        stageTela.setResizable(false);
        stageTela.show();

        return stageTela;
    }
    public static Stage chavePesoView() throws IOException {
        Stage stageTela = new Stage();
        FXMLLoader loader = new FXMLLoader();
        stageTela.initStyle(StageStyle.UNDECORATED);
        loader.setLocation(ViewService.class.getResource("chave-peso.fxml"));
        Parent content = loader.load();
        Scene telaView = new Scene(content);
        loader.getController();
        stageTela.setTitle("Configurar chave de peso");
        stageTela.setScene(telaView);
        stageTela.initModality(Modality.APPLICATION_MODAL);
        stageTela.setResizable(false);
        return stageTela;
    }
    public static Stage loginView() throws IOException {
        Stage loginStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loginStage.initStyle(StageStyle.UNDECORATED);
        loader.setLocation(ViewService.class.getResource("login.fxml"));
        Parent content = loader.load();
        Scene clienteCadastroView = new Scene(content);
        loader.getController();
        loginStage.setTitle("Cadastro de cliente");
        loginStage.setScene(clienteCadastroView);
        loginStage.setResizable(false);
        loginStage.show();
        return loginStage;
    }
    public static Stage notasPendentesView() throws IOException {
        Stage stageClienteCadastro = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("notasclientes.fxml"));
        Parent content = loader.load();
        Scene nc = new Scene(content);
        stageClienteCadastro.initModality(Modality.APPLICATION_MODAL);
        loader.getController();
        stageClienteCadastro.setTitle("Notas atrasadas");
        stageClienteCadastro.setScene(nc);
        Image icon = new Image("/neworder.png");
        stageClienteCadastro.getIcons().add(icon);
        stageClienteCadastro.setResizable(false);
        return stageClienteCadastro;
    }
    public static Stage buscarPedido() throws IOException {
        Stage stageBuscar = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewService.class.getResource("buscar.fxml"));
        stageBuscar.initModality(Modality.APPLICATION_MODAL);
        Parent content = loader.load();
        Scene nc = new Scene(content);
        loader.getController();
        stageBuscar.setTitle("Buscar");
        stageBuscar.setScene(nc);
        Image icon = new Image("notes.png");
        stageBuscar.getIcons().add(icon);
        stageBuscar.setResizable(false);
        return stageBuscar;
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

    public static Stage telaAdicionar(boolean verificaJanela, Label valorPeso) throws IOException {
        Stage stageAdicionar = new Stage();
        if (!verificaJanela) {
            AdicionarControler.peso = valorPeso.getText();
            stageAdicionar.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader segundaTela = new FXMLLoader();
            segundaTela.setLocation(ViewService.class.getResource("adicionar.fxml"));
            Parent content = segundaTela.load();
            Scene tela2 = new Scene(content);
            segundaTela.getController();
            stageAdicionar.setTitle("Adicionar pedido");
            stageAdicionar.setScene(tela2);
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

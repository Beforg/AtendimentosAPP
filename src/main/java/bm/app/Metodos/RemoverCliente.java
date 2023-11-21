package bm.app.Metodos;

import bm.app.Model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RemoverCliente {
    public void removerCliente(ObservableList<Cliente> list){
        ObservableList<Cliente> removeCliente = FXCollections.observableArrayList();

        for (Cliente cliente : list){
            if (cliente.getRemover().isSelected())
            {
                removeCliente.add(cliente);
            }
        }
        if (removeCliente.isEmpty()) {
            Alert removerVazio = new Alert(Alert.AlertType.ERROR);
            removerVazio.setTitle("Erro");
            removerVazio.setHeaderText("Nenhum atendimento selecionado");
            Stage alertStage = (Stage) removerVazio.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("botao-x.png"));
            removerVazio.showAndWait();

        } else {
            Alert confirmaRemover = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaRemover.setTitle("Remover atendimento");
            confirmaRemover.setHeaderText("Confirmar");
            Stage alertStage = (Stage) confirmaRemover.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("aviso.png"));
            confirmaRemover.setContentText("Tem certeza que deseja remover os atendimentos selecionados? os atendimentos excluídos não poderão ser recuperados.");

            confirmaRemover.getButtonTypes().stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .ifPresent(buttonType -> {
                        Button button = (Button) confirmaRemover.getDialogPane().lookupButton(buttonType);
                        button.setDefaultButton(false);

                    });

            if (confirmaRemover.showAndWait().get() == ButtonType.OK) {
                list.removeAll(removeCliente);

            }
        }
    }
}

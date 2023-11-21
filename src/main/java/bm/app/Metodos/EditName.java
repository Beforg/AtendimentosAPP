package bm.app.Metodos;

import bm.app.Model.Cliente;
import javafx.scene.control.*;

public class EditName {
    public void editName(TableView<Cliente> tabelaCliente, TableColumn.CellEditEvent<Cliente, String> nomeClienteTroca) {
        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();
        String oldName = cliente.getNome();
        System.out.println(oldName);
        cliente.setNome(nomeClienteTroca.getNewValue());
        if (cliente.getNome().equals("")) {
            cliente.setNome(oldName);
            tabelaCliente.refresh();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("Ocorreu um erro na aplicação");
            alert.setContentText("Preencha o nome para continuar");
            alert.getButtonTypes().stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .ifPresent(buttonType -> {
                        Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
                        button.setDefaultButton(false);

                    });
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-size: 14px; -fx-font-family: Arial, sans-serif;");
            alert.showAndWait();
        }

    }
}

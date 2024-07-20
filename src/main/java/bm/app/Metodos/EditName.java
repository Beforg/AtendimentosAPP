package bm.app.Metodos;

import bm.app.Model.pedidos.PedidoTableView;
import javafx.scene.control.*;

public class EditName {
    public void editName(TableView<PedidoTableView> tabelaCliente, TableColumn.CellEditEvent<PedidoTableView, String> nomeClienteTroca) {
        PedidoTableView pedidoTableView = tabelaCliente.getSelectionModel().getSelectedItem();
        String oldName = pedidoTableView.getNome();
        System.out.println(oldName);
        pedidoTableView.setNome(nomeClienteTroca.getNewValue());
        if (pedidoTableView.getNome().equals("")) {
            pedidoTableView.setNome(oldName);
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

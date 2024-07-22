package bm.app.Utils;

import bm.app.Model.cliente.Cliente;
import bm.app.Model.pedidos.Pedido;
import javafx.scene.control.*;

public class Validacao {
    public static boolean validarCamposAdicionarPedido(Pedido pedido, boolean pedidoFeitoCheckBox) {
        if (pedido.getNome().isEmpty() && pedidoFeitoCheckBox) {
            CaixaDeMensagem.mensagemErro("Erro", "Nome do pedido não pode ser vazio", "Por favor, insira um nome para o pedido.");
            throw new IllegalArgumentException("Pedido no formato Inválido");
        } else {
            if (pedido.getFormaPagamento() == null || pedido.getChavePeso().isEmpty()) {
                CaixaDeMensagem.mensagemErro("Erro", "Campos obrigatórios não preenchidos", "Por favor, preencha todos os campos obrigatórios.");
                throw new IllegalArgumentException("Pedido no formato Inválido");
            }
            return true;
        }
    }
    public static Cliente verificaComboBoxAdicionarCliente(ComboBox<Cliente> comboBox) {
        if (comboBox.getSelectionModel().isEmpty()) {
            return null;
        }
        return comboBox.getSelectionModel().getSelectedItem();
    }
}

package bm.app.Model.notas;

import bm.app.Infra.dao.NotasClienteDAO;
import bm.app.Model.anotacoes.Notas;
import bm.app.Model.pedidos.Pedido;
import bm.app.Model.pedidos.PedidoTableView;

import java.util.ArrayList;
import java.util.List;

public class NotasClienteService {
    public void registrarNotasCliente(List<Pedido> pedido, NotasClienteDAO notasClienteDAO) {
        List<NotasCliente> pedidosNaoPagos = new ArrayList<>();
        for (Pedido pedidoVer : pedido ) {
            if (notasClienteDAO.verificaCodigoPedidoJaRegistrado(pedidoVer.getId())) {
                return;
            } else {
                NotasCliente nota = new NotasCliente(pedidoVer);
                pedidosNaoPagos.add(nota);
            }
        }

        notasClienteDAO.registrarNotaCliente(pedidosNaoPagos);
    }
    public void removerNotaCliente(NotasCliente nota, NotasClienteDAO notasClienteDAO) {
        notasClienteDAO.removerNota(nota);
    }
}

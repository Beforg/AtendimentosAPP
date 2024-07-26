package bm.app.Model.notas;

import bm.app.Model.pedidos.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotasCliente {

    private String nomeCliente;
    private String idCliente;
    private String idPedido; // para consultas dps
    private String dataPedido;
    private BigDecimal valorPedido;
    private String chavePeso;

    public NotasCliente(Pedido pedidoVer) {
        this.nomeCliente = pedidoVer.getCliente().getNome();
        this.idCliente = String.valueOf(pedidoVer.getCliente().getId());
        this.idPedido = String.valueOf(pedidoVer.getId());
        this.dataPedido = String.valueOf(pedidoVer.getDataPedido());
        this.valorPedido = pedidoVer.getValor();
        this.chavePeso = pedidoVer.getChavePeso();
    }



}

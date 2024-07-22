package bm.app.Model.pedidos;

import bm.app.Model.cliente.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    private UUID id;
    private String nome;
    private BigDecimal valor;
    private String formaPagamento;
    private LocalDate dataPedido;
    private String entregador;
    private String statusPedido;
    private boolean pago;
    private boolean entregue;
    private String chavePeso;
    private Cliente cliente;
    private LocalTime horaPedido;

    public Pedido(UUID id, String nome, BigDecimal valor, String formaPagamento,
                  String entregador, String statusPedido, String chavePeso, Cliente cliente) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.horaPedido = LocalTime.now();
        this.dataPedido = LocalDate.now();
        this.entregador = entregador;
        this.statusPedido = statusPedido;
        this.chavePeso = chavePeso;
        this.cliente = cliente;


    }

    public Pedido(PedidoTableView pedidoTableView) {
        this.id = pedidoTableView.getId();
        this.nome = pedidoTableView.getNome();
        this.valor = pedidoTableView.getBrl();
        this.formaPagamento = pedidoTableView.getFormaPagamento();
        this.dataPedido = pedidoTableView.getDataPedido();
        this.entregador = pedidoTableView.getEntregador();
        this.statusPedido = pedidoTableView.getStatus();
        this.pago = pedidoTableView.isPago();
        this.entregue = pedidoTableView.isEntregue();
        this.chavePeso = pedidoTableView.getChavePeso();
    }

    public boolean verificaPago() {
        return pago;
    }

    public boolean verificaEntregue() {
        return entregue;
    }

    public void atualizarValor(PedidoTableView pedidoTableView) {
        this.valor = pedidoTableView.getBrl();
        this.statusPedido = pedidoTableView.getStatus();
    }

    public BigDecimal converteValorParaPeso() {
        return valor.multiply(new BigDecimal(chavePeso.replace(",", ".")));
    }
}

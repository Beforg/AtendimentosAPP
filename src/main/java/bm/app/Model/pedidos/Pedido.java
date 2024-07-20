package bm.app.Model.pedidos;

import bm.app.Model.FormaPagamento;
import bm.app.Model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pedido {

    private UUID id;
    private String nome;
    private BigDecimal valor;
    private String formaPagamento;
    private LocalDateTime dataPedido;
    private String entregador;
    private String statusPedido;
    private boolean pago;
    private boolean entregue;
    private String chavePeso;

    public Pedido(UUID id, String nome, BigDecimal valor, String formaPagamento, LocalDateTime dataPedido, String entregador, String statusPedido, String chavePeso) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.dataPedido = dataPedido;
        this.entregador = entregador;
        this.statusPedido = statusPedido;
        this.pago = false;
        this.entregue = false;
        this.chavePeso = chavePeso;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void atualizaNomePedido(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void atualizaValorPedido(BigDecimal valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void atualizaFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getEntregador() {
        return entregador;
    }

    public void atualizaEntregador(String entregador) {
        this.entregador = entregador;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void atualizaStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public boolean verificaPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean verificaEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

    public void pagar() {
        this.pago = true;
    }

    public void entregar() {
        this.entregue = true;
    }

    public String getChavePeso() {
        return chavePeso;
    }

    public void setChavePeso(String chavePeso) {
        this.chavePeso = chavePeso;
    }
    public BigDecimal converteValorParaPeso() {
        return valor.multiply(new BigDecimal(chavePeso.replace(",", ".")));
    }
}

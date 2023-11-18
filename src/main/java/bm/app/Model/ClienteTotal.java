package bm.app.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

public class ClienteTotal {
    private final IntegerProperty atendimentos = new SimpleIntegerProperty();
    private final IntegerProperty pedidos = new SimpleIntegerProperty();
    private final IntegerProperty entregues = new SimpleIntegerProperty();
    private BigDecimal valorPix;
    private BigDecimal valorUyu;
    private BigDecimal valorCartao;
    private final ObjectProperty<BigDecimal> valorTotal = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> valorBrl = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> valorNaoRecebido = new SimpleObjectProperty<>();

    public ClienteTotal(int atendimentos, int pedidos, BigDecimal valorTotal, BigDecimal valorBrl, BigDecimal valorPix, BigDecimal valorUyu, BigDecimal valorNaoRecebido, BigDecimal valorCartao, int entregues) {
        this.atendimentos.set(atendimentos);
        this.pedidos.set(pedidos);
        this.valorTotal.set(valorTotal);
        this.valorBrl.set(valorBrl);
        this.valorPix = valorPix;
        this.valorUyu = valorUyu;
        this.valorCartao = valorCartao;
        this.valorNaoRecebido.set(valorNaoRecebido);
        this.entregues.set(entregues);
    }


    public IntegerProperty atendimentosProperty() {
        return atendimentos;
    }
    public IntegerProperty pedidosProperty() {
        return pedidos;
    }
    public int getPedidos() {
        return pedidos.get();
    }
    public void setPedidos(int pedidos) {
        this.pedidos.set(pedidos);
    }

    public BigDecimal getValorTotal() {
        return valorTotal.get();
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal.get();
    }

    public BigDecimal getValorBrl() {
        return valorBrl.get();
    }

    public void setValorBrl(BigDecimal valorBrl) {
        this.valorBrl.set(valorBrl);
    }

    public BigDecimal getValorPix() {
        return valorPix;
    }

    public void setValorPix(BigDecimal valorPix) {
        this.valorPix = valorPix;
    }

    public BigDecimal getValorUyu() {
        return valorUyu;
    }

    public void setValorUyu(BigDecimal valorUyu) {
        this.valorUyu = valorUyu;
    }

    public BigDecimal getValorNaoRecebido() {
        return valorNaoRecebido.get();
    }

    public void setValorNaoRecebido(BigDecimal valorNaoRecebido) {
        this.valorNaoRecebido.set(valorNaoRecebido);
    }

    public int getAtendimentos() {
        return atendimentos.get();
    }

    public void setAtendimentos(int atendimentos) {
        this.atendimentos.set(atendimentos);
    }

    public int getEntregues() {
        return entregues.get();
    }

    public IntegerProperty entreguesProperty() {
        return entregues;
    }

    public void setEntregues(int entregues) {
        this.entregues.set(entregues);
    }

    public BigDecimal getValorCartao() {
        return valorCartao;
    }

    public void setValorCartao(BigDecimal valorCartao) {
        this.valorCartao = valorCartao;
    }

    public ObjectProperty<BigDecimal> valorTotalProperty() {
        return valorTotal;
    }

    public ObjectProperty<BigDecimal> valorBrlProperty() {
        return valorBrl;
    }

    public ObjectProperty<BigDecimal> valorNaoRecebidoProperty() {
        return valorNaoRecebido;
    }

}

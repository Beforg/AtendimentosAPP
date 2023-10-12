package bm.app.demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

public class ClienteTotal {
    private final IntegerProperty atendimentos = new SimpleIntegerProperty();
    private final IntegerProperty pedidos = new SimpleIntegerProperty();
    private BigDecimal valorPix;
    private BigDecimal valorUyu;
    private BigDecimal valorCartao;
    private final ObjectProperty<BigDecimal> valorTotal = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> valorBrl = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> valorNaoRecebido;

    public ClienteTotal(int atendimentos, int pedidos, BigDecimal valorTotal, BigDecimal valorBrl, BigDecimal valorPix, BigDecimal valorUyu, BigDecimal valorNaoRecebido, BigDecimal valorCartao) {
        this.atendimentos.set(atendimentos);
        this.pedidos.set(pedidos);
        this.valorTotal.set(valorTotal);
        this.valorBrl.set(valorBrl);
        this.valorPix = valorPix;
        this.valorUyu = valorUyu;
        this.valorCartao = valorCartao;
        this.valorNaoRecebido = new SimpleObjectProperty<>(valorTotal.subtract(valorBrl));
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
}

package bm.app.Model.pedidos;
import javafx.beans.property.*;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PedidoTableView {

    private  String nome;
    private String entregador;
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> brl = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> uyu = new SimpleObjectProperty<>();
    private UUID id;
    private LocalDate dataPedido;
    private final StringProperty formaPagamento = new SimpleStringProperty();
    private boolean pago;
    private boolean entregue;
    private CheckBox remover;
    private String chavePeso;


    public PedidoTableView(Pedido pedido) {
        this.id = pedido.getId();
        this.nome = pedido.getNome();
        this.status.set(pedido.getStatusPedido().toString());
        this.brl.set(pedido.getValor());
        this.uyu.set(pedido.converteValorParaPeso());
        this.formaPagamento.set(pedido.getFormaPagamento().toString());
        this.entregador = pedido.getEntregador();
        this.dataPedido = pedido.getDataPedido();
        this.remover = new CheckBox();
        this.pago = pedido.verificaPago();
        this.entregue = pedido.verificaEntregue();
        this.chavePeso = pedido.getChavePeso();
    }
    public PedidoTableView() {

    }


    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setBrl(BigDecimal brl) {
        this.brl.set(brl);
    }

    public void setUyu(BigDecimal uyu) {
        this.uyu.set(uyu);
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento.set(formaPagamento);
    }

    public String getStatus() {
        return status.get();
    }

    public BigDecimal getBrl() {
        return brl.get();
    }
    public ObjectProperty<BigDecimal> brlProperty() {
        return brl;
    }

    public BigDecimal getUyu() {
        return uyu.get();
    }

    public String getFormaPagamento() {
        return formaPagamento.get();
    }

}

package bm.app.Model.pedidos;

import bm.app.Controller.AppController;
import javafx.beans.property.*;
import javafx.scene.control.CheckBox;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoTableView {
    private  String nome;
    private String entregador;
    private  StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> brl = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> uyu = new SimpleObjectProperty<>();
    private UUID id;
    private LocalDateTime dataPedido;
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

//        brlProperty().addListener((observable, oldValue, newValue) -> {
//            AppController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false,"");
//        });
//        uyuProperty().addListener((observable, oldValue, newValue) -> {
//            AppController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false,"");
//
//        });
    }
    public PedidoTableView() {

    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

    public String getChavePeso() {
        return chavePeso;
    }

    public void setChavePeso(String chavePeso) {
        this.chavePeso = chavePeso;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    //    private void atualizarStatus() {
//        if (isEntregue() && isPago()) {
//            setStatus("Entregue");
//        } else if (isEntregue()) {
//            setStatus("Não pago");
//        } else if (isPago()) {
//            setStatus("Pago");
//        } else {
//            if (getBrl().equals(BigDecimal.ZERO)) {
//                setStatus("Atendendo");
//            } else {
//                setStatus("Pedido");
//            }
//        }
//    }

//    public boolean isEntregue() {
//        return entregue.get();
//    }
//
//    public BooleanProperty entregueProperty() {
//        return entregue;
//    }
//
//    public void setEntregue(boolean entregue) {
//        this.entregue.set(entregue);
//    }

    public StringProperty formaPagamentoProperty() {
        return formaPagamento;
    }

//  //  public boolean isPago() {
//        return pago.get();
//    }
//
//    public BooleanProperty pagoProperty() {
//        return pago;
//    }
//
//    public void setPago(boolean pago) {
//        this.pago.set(pago);
//    }

    public CheckBox getRemover() {
        return remover;
    }

    public void setRemover(CheckBox remover) {
        this.remover = remover;
    }

    public void setNome(String nome) {
        this.nome = nome;
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


    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public ObjectProperty<BigDecimal> uyuProperty() {
        return uyu;

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


    public String getEntregador() {
        return entregador;
    }

    public void setEntregador(String entregador) {
        this.entregador = entregador;
    }
}

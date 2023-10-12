package bm.app.demo;

import bm.app.demo.HelloController;
import javafx.beans.property.*;
import javafx.scene.control.CheckBox;

import java.math.BigDecimal;

public class Cliente {
    private  String nome;
   private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> brl = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> uyu = new SimpleObjectProperty<>();

    private final StringProperty formaPagamento = new SimpleStringProperty();
    private final BooleanProperty pago = new SimpleBooleanProperty();

    private final BooleanProperty entregue = new SimpleBooleanProperty();
    private CheckBox remover;

    public Cliente(String nome, String status, BigDecimal brl, BigDecimal uyu, String formaPagamento, Boolean pago, Boolean entregue) {
        this.nome = nome;
        this.status.set(status);
        this.brl.set(brl);
        this.uyu.set(uyu);
        this.formaPagamento.set(formaPagamento);
        this.pago.set(pago);
        this.entregue.set(entregue);
        this.remover = new CheckBox();
        brlProperty().addListener((observable, oldValue, newValue) -> {
            HelloController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false);
        });
        uyuProperty().addListener((observable, oldValue, newValue) -> {
            HelloController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false);
        });
        pagoProperty().addListener((observable, oldValue, newValue) -> {
            HelloController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false);
        });
        statusProperty().addListener((observable, oldValue, newValue) -> {
            HelloController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false);
        });
        formaPagamentoProperty().addListener((observable, oldValue, newValue) -> {
            HelloController.atualizarAtendimento("","", BigDecimal.ZERO, BigDecimal.ZERO, "",false,false);
        });
    }

    public boolean isEntregue() {
        return entregue.get();
    }

    public BooleanProperty entregueProperty() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue.set(entregue);
    }

    public StringProperty formaPagamentoProperty() {
        return formaPagamento;
    }

    public boolean isPago() {
        return pago.get();
    }

    public BooleanProperty pagoProperty() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago.set(pago);
    }

    public CheckBox getRemover() {
        return remover;
    }

    public void setRemover(CheckBox remover) {
        this.remover = remover;
    }

    public Boolean getEntregue() {
        return entregue.get();
    }

    public void setEntregue(Boolean entregue) {
        this.entregue.set(entregue);
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

    public void setPago(Boolean pago) {
        this.pago.get();
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

    public Boolean getPago() {
        return pago.get();
    }


}

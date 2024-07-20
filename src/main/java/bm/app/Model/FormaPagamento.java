package bm.app.Model;

public enum FormaPagamento {
    REAIS("Reais"),
    CARTAO("Cartão"),
    PIX("Pix"),
    CHEQUE("Cheque"),
    BOLETO("Boleto"),
    PESOS("Pesos");

    public final String formaPagamento;

    FormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }
}

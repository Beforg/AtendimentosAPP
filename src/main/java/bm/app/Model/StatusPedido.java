package bm.app.Model;

public enum StatusPedido {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    ENTREGUE("Entregue"),
    PAGO("Pago");

    public final String statusPedido;

    StatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }
}

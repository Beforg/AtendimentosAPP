package bm.app.Model.notas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotasClienteTableView {

    private String data;
    private String nomeCliente;
    private String valorPedido;
    private String chavePeso;
    private String idPedido;


    public NotasClienteTableView(NotasCliente nota) {
        this.data = nota.getDataPedido();
        this.nomeCliente = nota.getNomeCliente();
        this.valorPedido = "R$ " + nota.getValorPedido();
        this.chavePeso = nota.getChavePeso();
        this.idPedido = nota.getIdPedido();
    }


}

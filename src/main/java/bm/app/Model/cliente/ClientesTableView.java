package bm.app.Model.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientesTableView {

    private String id;
    private String nome;
    private String endereco;
    private String telefone;

    public ClientesTableView(Cliente cliente) {
        this.id = String.valueOf(cliente.getId());
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.endereco = cliente.getEndereco();
    }


}

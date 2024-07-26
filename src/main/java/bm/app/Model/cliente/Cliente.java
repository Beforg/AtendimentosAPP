package bm.app.Model.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private int id;
    private String nome;
    private String telefone;
    private String endereco;

    @Override
    public String toString() {
        return nome;
    }

    public Cliente (String nome, String telefone, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Cliente (ClientesTableView clientesTableView) {
        this.id = Integer.parseInt(clientesTableView.getId());
        this.nome = clientesTableView.getNome();
        this.telefone = clientesTableView.getTelefone();
        this.endereco = clientesTableView.getEndereco();
    }
}

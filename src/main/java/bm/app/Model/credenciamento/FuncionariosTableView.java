package bm.app.Model.credenciamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionariosTableView {
    private String id;
    private String nome;
    private String username;
    private String password;

    public FuncionariosTableView(Credenciamento credenciamento) {
        this.id = String.valueOf(credenciamento.getId());
        this.nome = credenciamento.getNome();
        this.username = credenciamento.getUsername();
        this.password = credenciamento.getPassword();
    }
}

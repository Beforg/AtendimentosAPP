package bm.app.Model.credenciamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Credenciamento {

    private int id;
    private String nome;
    private String username;
    private String password;
    private Cargo cargo;

    public Credenciamento(String nome, String username, String password, Cargo cargo) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.cargo = cargo;
    }

}


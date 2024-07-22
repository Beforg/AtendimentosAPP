package bm.app.Model.credenciamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credenciamento {

    private UUID id;
    private String nome;
    private String username;
    private String password;
    private Cargo cargo;


}


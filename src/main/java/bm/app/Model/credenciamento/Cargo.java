package bm.app.Model.credenciamento;

public enum Cargo {
    ADMINISTRADOR("Administrador"),
    FUNCIONARIO("Funcionário"),
    MASTER("Master");

    private String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

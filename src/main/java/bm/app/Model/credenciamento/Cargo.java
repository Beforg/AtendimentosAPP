package bm.app.Model.credenciamento;

public enum Cargo {
    ADMINISTRADOR("Administrador"),
    FUNCIONARIO("Funcionário");

    private String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

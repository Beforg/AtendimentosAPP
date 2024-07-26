package bm.app.Model.anotacoes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class Notas {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataEdicao;

    public Notas( String titulo, String descricao, LocalDate dataCriacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;

    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Data de Criação: " + dataCriacao + "\n" +
                "Data de Edição: " + dataEdicao + "\n";
    }

}

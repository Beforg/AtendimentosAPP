package bm.app.Model.notas;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notas {
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEdicao;

    public Notas(UUID id, String titulo, String descricao, LocalDateTime dataCriacao, LocalDateTime dataEdicao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Data de Criação: " + dataCriacao + "\n" +
                "Data de Edição: " + dataEdicao + "\n";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(LocalDateTime dataEdicao) {
        this.dataEdicao = dataEdicao;
    }
}

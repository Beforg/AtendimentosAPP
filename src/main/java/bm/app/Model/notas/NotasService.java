package bm.app.Model.notas;

import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotasService {
    public void criarNota(String titulo, String descricao, ObservableList<Notas> notasList) {
        UUID id = UUID.randomUUID();
        Notas notas = new Notas(id,titulo, descricao, LocalDateTime.now(), LocalDateTime.now());
        notasList.add(notas);

    }
    public void removerNota(ListView<Notas> list, ObservableList<Notas> notasList) {
        if (list.getSelectionModel().isEmpty()) {
            CaixaDeMensagem.mensagemErro("Erro","Erro ao remover nota","Selecione uma nota para remover");
            return;
        }
        Notas nota = list.getSelectionModel().getSelectedItem();
        notasList.remove(nota);
    }
    public void editarNota(ListView<Notas> listaDeNotas, TextField tfTituloNota, TextArea anotacoes, Pane pane) {
        Notas nota = listaDeNotas.getSelectionModel().getSelectedItem();
        nota.setTitulo(tfTituloNota.getText());
        nota.setDescricao(anotacoes.getText());
        listaDeNotas.refresh();
        AppUtils.adicionarNotasView(pane,listaDeNotas,true,false,tfTituloNota);
        AppUtils.limpaCamposNotas(tfTituloNota, anotacoes);
    }
}

package bm.app.Model.notas;

import bm.app.Infra.dao.NotasDAO;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class NotasService {
    public void criarNota(String titulo, String descricao, ObservableList<Notas> notasList, NotasDAO notasDAO) {
        Notas notas = new Notas(titulo, descricao, LocalDate.now());
        notasList.add(notas);
        notasDAO.criarNota(notas);

    }
    public void removerNota(ListView<Notas> list, ObservableList<Notas> notasList, NotasDAO notasDAO) {
        if (list.getSelectionModel().isEmpty()) {
            CaixaDeMensagem.mensagemErro("Erro","Erro ao remover nota","Selecione uma nota para remover");
            return;
        }
        Notas nota = list.getSelectionModel().getSelectedItem();
        notasList.remove(nota);
        notasDAO.removerNota(nota);
        list.refresh();
    }
    public void editarNota(ListView<Notas> listaDeNotas, TextField tfTituloNota, TextArea anotacoes, Pane pane, NotasDAO notasDAO) {
        Notas nota = listaDeNotas.getSelectionModel().getSelectedItem();
        nota.setTitulo(tfTituloNota.getText());
        nota.setDescricao(anotacoes.getText());
        nota.setDataEdicao(LocalDate.now());
        listaDeNotas.refresh();
        AppUtils.adicionarNotasView(pane,listaDeNotas,true,false,tfTituloNota);
        AppUtils.limpaCamposNotas(tfTituloNota, anotacoes);
        notasDAO.editarNota(nota);

    }
}

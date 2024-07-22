package bm.app.Utils;

import bm.app.Model.pedidos.PedidoTableView;
import bm.app.Model.pedidos.PedidoTotalTableView;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GerarRelatorio {
    public void salvarPDF(TableView<PedidoTotalTableView> tabelaTotal, ObservableList<PedidoTableView> list, String horaAtual, TextField nomeFuncionario, TextField valorPeso, TextArea anotacoes, Button botaoFinalizar) {
        TableColumn<PedidoTotalTableView, Integer> colunaAtendimentos = (TableColumn<PedidoTotalTableView, Integer>) tabelaTotal.getColumns().get(0);
        String atendimentos = String.valueOf(colunaAtendimentos.getCellData(tabelaTotal.getItems().get(0)));

        TableColumn<PedidoTotalTableView, Integer> colunaPedidos = (TableColumn<PedidoTotalTableView, Integer>) tabelaTotal.getColumns().get(1);
        String pedidos = String.valueOf(colunaPedidos.getCellData(tabelaTotal.getItems().get(0)));

        TableColumn<PedidoTotalTableView, BigDecimal> colunaVtotal = (TableColumn<PedidoTotalTableView, BigDecimal>) tabelaTotal.getColumns().get(3);
        BigDecimal valorNumTot = colunaVtotal.getCellData(tabelaTotal.getItems().get(0));
        String valorTotal = String.format("%.2f", valorNumTot);

        TableColumn<PedidoTotalTableView, BigDecimal> colunaRecebidoBrl = (TableColumn<PedidoTotalTableView, BigDecimal>) tabelaTotal.getColumns().get(4);
        BigDecimal valorBrlNum = colunaRecebidoBrl.getCellData(tabelaTotal.getItems().get(0));
        String valorBrl =  String.format("%.2f", valorBrlNum);


        TableColumn<PedidoTotalTableView, BigDecimal> colunaRecebidoPIX = (TableColumn<PedidoTotalTableView, BigDecimal>) tabelaTotal.getColumns().get(5);
        BigDecimal valorPixNum = colunaRecebidoPIX.getCellData(tabelaTotal.getItems().get(0));
        String valorPix = String.format("%.2f", valorPixNum);

        TableColumn<PedidoTotalTableView, BigDecimal> colunaRecebidoUYU = (TableColumn<PedidoTotalTableView, BigDecimal>) tabelaTotal.getColumns().get(7);
        BigDecimal valorUyuNum = colunaRecebidoUYU.getCellData(tabelaTotal.getItems().get(0));
        String valorUyu = String.format("%.2f", valorUyuNum);

        TableColumn<PedidoTotalTableView, BigDecimal> colunaRecebidoCartao = (TableColumn<PedidoTotalTableView, BigDecimal>) tabelaTotal.getColumns().get(6);
        BigDecimal valorCartaoNum = colunaRecebidoCartao.getCellData(tabelaTotal.getItems().get(0));
        String valorCartao = String.format("%.2f", valorCartaoNum);



        try (PDDocument document = new PDDocument()) {
            float margin = 30;
            float yPosition = 0;
            float marginP = 20;
            int pageNumber = 1;

            PDPage page = new PDPage();

            for (PedidoTableView pedidoTableView : list) {
                if (yPosition == 0) {

                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                        yPosition = page.getMediaBox().getHeight() - marginP;

                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText("Nome");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Status");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("BRL");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("UYU");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Pago com");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Entregue");
                        contentStream.newLineAtOffset(85, 0);
                        contentStream.showText("Pago");
                        contentStream.endText();
                    }
                    yPosition -= 20;
                }

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(pedidoTableView.getNome());
                    contentStream.newLineAtOffset(85, 0);;
                    contentStream.showText("  " + pedidoTableView.getStatus().replace("Atendendo", "Não pediu"));
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(String.valueOf(pedidoTableView.getBrl()) + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(String.valueOf(pedidoTableView.getUyu()) + " $");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(pedidoTableView.getFormaPagamento());
                    contentStream.newLineAtOffset(85, 0);
                    //contentStream.showText(String.valueOf(pedidoTableView.getEntregue()).replace("true", "SIM").replace("false","NÃO"));
                    contentStream.newLineAtOffset(85, 0);
                   // contentStream.showText(String.valueOf(pedidoTableView.getPago()).replace("true", "SIM").replace("false","NÃO"));

                    contentStream.endText();

                    yPosition -= 20;

                    if (yPosition < marginP) {
                        yPosition = 0;
                        pageNumber++;

                    }

                }
            }
            yPosition -= 101;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                System.out.println(marginP);
                System.out.println(yPosition);
                if (yPosition < marginP) {
                    pageNumber++;
                    System.out.println("MPVA");
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    try (PDPageContentStream contentStream2 = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                        yPosition = page.getMediaBox().getHeight() - marginP;
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);

                        contentStream2.newLineAtOffset(margin, yPosition);
                        contentStream2.showText("Atendimentos");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("   Pedidos");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("Valor total");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("R$ Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("$ Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("PIX Recebido");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText("Cartão");

                        contentStream2.setFont(PDType1Font.HELVETICA, 10);
                        contentStream2.newLineAtOffset(-510, -20);
                        contentStream2.showText(atendimentos + " atendimentos");
                        contentStream2.newLineAtOffset(85, 0);;
                        contentStream2.showText(pedidos + " pedido(s)");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorTotal + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorBrl + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorUyu + " $");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorPix + " R$");
                        contentStream2.newLineAtOffset(85, 0);
                        contentStream2.showText(valorCartao + " R$");

                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream2.newLineAtOffset(-497, -40);
                        contentStream2.showText("| Data do fechamento: " + horaAtual + " | Funcionário: " + nomeFuncionario.getText() + " | Chave do peso do dia: " + valorPeso.getText() + " |");

                        contentStream2.setFont(PDType1Font.HELVETICA, 7);
                        contentStream2.newLineAtOffset(175, -20);
                        contentStream2.showText("developed by @beforg | https://github.com/Beforg");
                        contentStream2.endText();
                    }

                } else {

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(margin, yPosition+81);
                    contentStream.showText("Atendimentos");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("   Pedidos");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("Valor total");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("R$ Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("$ Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("PIX Recebido");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText("Cartão");

                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(-510, -20);
                    contentStream.showText(atendimentos + " atendimento(s)");
                    contentStream.newLineAtOffset(85, 0);;
                    contentStream.showText(pedidos + " pedido(s)");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorTotal + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorBrl + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorUyu + " $");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorPix + " R$");
                    contentStream.newLineAtOffset(85, 0);
                    contentStream.showText(valorCartao + " R$");

                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(-497, -40);
                    contentStream.showText("| Data do fechamento: " + horaAtual + " | Funcionário: " + nomeFuncionario.getText() + " | Chave do peso do dia: " + valorPeso.getText() + " |");
                    contentStream.setFont(PDType1Font.HELVETICA, 7);

                    contentStream.newLineAtOffset(175, -20);;
                    contentStream.showText("developed by @beforg | https://github.com/Beforg");

                    contentStream.endText();

                }
            }

            PDPage anotationPage = new PDPage(PDRectangle.A4);
            document.addPage(anotationPage);
            try {
                PDPageContentStream contentStream = new PDPageContentStream(document, anotationPage);
                float fontSize = 12;
                PDFont font = PDType1Font.COURIER_BOLD;
                contentStream.setFont(font, fontSize);
                String texto = anotacoes.getText();
                contentStream.beginText();
                float posX = 0, posY = 800;
                int contador = 0 ;
                contentStream.newLineAtOffset(20,posY);
                for (int i = 0; i < texto.length(); i++) {

                    contentStream.showText(String.valueOf(texto.charAt(i)).replace("\n",""));
                    contador++;
                    posX += 7.5;

                    if (posX > 577.5) {
                        contentStream.endText();
                        System.out.println("Entrou no if");
                        posY -= 15;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(20, posY);
                        posX = 0;
                    }

                    System.out.println(contador);
                    System.out.println(posX);


                }
                contentStream.endText();
                contentStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            float fontSize = 10;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float marginBottom = 10;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset((pageWidth / 2) - 20, marginBottom);
                contentStream.showText("Página " + pageNumber);
                contentStream.endText();
            }
            nomeFuncionario.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar PDF");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            String dataAtual = dateFormat.format(new Date());
            String nomeArquivo = "caixa_" + dataAtual + ".pdf";
            fileChooser.setInitialFileName(nomeArquivo);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage primaryStage = (Stage) botaoFinalizar.getScene().getWindow();
            File fileToSave = fileChooser.showSaveDialog(primaryStage);

            if (fileToSave != null) {
                try {
                    document.save(fileToSave.getAbsolutePath());
                    document.close();
                    System.out.println("Tabela de clientes salva em " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cancelado pelo usuário");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
    }


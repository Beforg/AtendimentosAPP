# Aplicação de Atendimentos
Projeto JavaFX para auxiliar nos atendimentos e fechamento do caixa
| :placard: Vitrine.Dev |  @Beforg   |
| -------------  | --- |
| :sparkles: Nome        | **Atendimentos APP**
| :label: Tecnologias | java, screenbuilder
| :date: Data da conclusão       | 12 de outubro de 2023
| :back: Download do projeto | [**LINK PARA O PROJETO EM .EXE**](https://drive.google.com/file/d/1P8tc2-abWZI6XEi-sp7PYJ9DBZXv2XWH/view?usp=drive_link)
| :balloon: Versão do projeto | Versão 2.0 (20 de novembro de 2023)

<!-- Inserir imagem com a #vitrinedev ao final do link -->
![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170029286.png#vitrinedev)

## Descrição
Projeto criado com base nas necessidades do dia a dia do meu antigo trabalho, uma aplicação JavaFX desenvolvida para auxiliar nos atendimentos, registrar os atendimentos e pedidos, exibindo a forma de pagamento, seu valor e marcando se o pedido final foi pago ou entregue, salvando os dados em formato de PDF ao finalizar o horário comercial.

## Detalhes do projeto

### Aplicação

A aplicação apresenta duas tabelas (TableView), uma para todos os atendimentos adicionados e outra para calcular os valores totais

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170132543.png)

Adicionando um atendimento, podemos manipular os valores e a forma de pagameto de acordo com o pedidio que foi feito, sendo mostrado também a situação do pedido (na coluna **STATUS**) com as opções *ATENDIMENTO* ou *PEDIDO*, a situação muda automaticamento quando agregamos um valor a esse atendimento, reconhecendo que o cliente fez um pedido, podemos também excluir um atendimento se necessário preenchendo a coluna " X " e acionando o botão remover.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170513989.png)

Nas colunas da tabela é possivel alterar atributos quando necessário, conforme é adicionado atendimentos, a segunda tabela cuida da soma dos valores totais de todos atendimentos, bem como os valores recebidos em cada forma de pagamento quando a coluna **PAGO** é marcada

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170554919.png)

De acordo com a marcação das colunas **PAGO** e **ENTREGUE**, a cor da linha da tabela muda, sendo o tom **VERMELHO** para pedidos que foram **ENTREGUES** e **NÃO PAGOS** e **VERDE** quando já foi PAGO, independente de ser entregue ou não, também muda o Status do atendimento de acordo com a marcação das colunas.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170711789.png)

Na parte superior temos os botões "Adicionar" para adicionar um atendimento, "Remover" para remover o atendimento selecionado e "Finalizar" para salvar os registros do dia, 
também tem o campo para o preenchimento da **CHAVE DO PESO** pois nesse caso é necessário a conversão de **REAIS** para **PESOS** por necessidade de receber alguns pagamentos em pesos uruguaios e o campo de preenchimento do **NOME DO FUNCIONÁRIO**, 
com finalidade de apresentar esses dados no momento de finalizar os atendimentos.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170731827.png)

### Anotações e promoções

Aqui temos um espaço para registrar anotações que vão sair no PDF gerado ao finalizar, além de ter um espaço para carregar a imagem das promoções do dia.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170755658.png)

### Adicinando e editando atendimentos

Ao acionar o botão "Adicionar" ou apertando a tecla ENTER, entramos na tela para adicionar:

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170823463.png)

Nessa tela, é pego a informação da **CHAVE DO PESO** inserida na tela principal, podendo ser alterada para aquele pedido específico quando necessário, sem alterar o valor da chave na tela principal.
Os valores de pesos uruguaios são convertidos automaticamente de acordo com a chave inserida, não sendo possível alterar o campo diretamente na tabela.

<p>Podemos também marcar a situação do atendimento como <strong>PEDIDO</strong> e ele vai entrar na tabela já com o status de <strong>PEDIDO</strong> para indicar que o cliente fez o pedido.</p>
<p>Podemos alterar os campos <strong>NOME</strong>, <strong>VALOR EM BRL</strong> e <strong>FORMA DE PAGAMENTO</strong> de acordo com o necessário, lembrando que quando o <strong>PAGO</strong> é marcado, a segunda tabela soma a coluna de acordo com a forma de pagamento selecionada.</p>

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170846017.png)

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170943691.png)

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_171020150.png)


### Finalizando os atendimentos
Ao acionar o botão **FINALIZAR**, é aberto uma janela para salvar o arquivo gerado em PDF, o nome padrão do arquivo é *caixa_(**informa o dia atual aqui**).pdf* para manter organizado os as informações do caixa

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_182938450.png)

Após salvar o documento PDF, esse é o resultado final:

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_183825714.png)

É apresentado a tabela de todos atendimentos com a tabela total, sendo reposicionado o status de ATENDENDO por NÃO PEDIU, o PDF vai adicionando mais páginas quando necessário.

## Considerações finais
Esse projeto foi construído de acordo com a necessidade diária, para melhorar e organizar os atendimentos da loja e evitar confusões na hora do fechamento do caixa, para esse projeto foi usado Java 17 e ScreenBuilder para a GUI do projeto.

*Créditos dos icones usados no programa:*

<a href="https://www.flaticon.com/br/icones-gratis/atendimento-ao-cliente" title="atendimento ao cliente ícones">Atendimento ao cliente ícones criados por IYAHICON - Flaticon</a> <br/>
<a href="https://www.flaticon.com/br/icones-gratis/do-utilizador" title="do utilizador ícones">Do utilizador ícones criados por uicon - Flaticon</a> <br/>
<a href="https://www.flaticon.com/br/icones-gratis/perguntas-frequentes" title="perguntas frequentes ícones">Perguntas frequentes ícones criados por Bharat Icons - Flaticon</a> <br/>
<a href="https://www.flaticon.com/br/icones-gratis/erro" title="erro ícones">Erro ícones criados por surang - Flaticon</a> <br/>
<a href="https://www.flaticon.com/br/icones-gratis/cancelar" title="cancelar ícones">Cancelar ícones criados por Alfredo Hernandez - Flaticon</a> <br/>
<a href="https://www.flaticon.com/br/icones-gratis/informacao" title="informação ícones">Informação ícones criados por Anggara - Flaticon</a> <br/>

## Copyright

MIT License

Copyright (c) 2023 Bruno Forgiarini

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


# Aplicação de Atendimentos
Projeto JavaFX para auxiliar nos atendimentos e fechamento do caixa
| :placard: Vitrine.Dev |  @Beforg   |
| -------------  | --- |
| :sparkles: Nome        | **Atendimentos APP**
| :label: Tecnologias | java, javafx, maven, lombok, postgresql, flyway, css.
| :date: Data da conclusão       | 12 de outubro de 2023
| :back: Download do projeto | 
| :balloon: Versão do projeto | Versão 3.0 (26 de jul. 2024)

<!-- Inserir imagem com a #vitrinedev ao final do link -->
![](https://github.com/Beforg/assets/blob/main/atendapp3.0/app.png) `versão 3.0`

## Descrição
Projeto criado com base nas necessidades do dia a dia do meu antigo trabalho, uma aplicação JavaFX desenvolvida para auxiliar nos atendimentos, registrar os atendimentos e pedidos, exibindo a forma de pagamento, seu valor e marcando se o pedidoTableView final foi pago ou entregue, salvando os dados em formato de PDF ao finalizar o horário comercial.

## Detalhes do projeto

#### Versão 3.0:

- Adicionado Banco de Dados (PostgreSQL)
- Funcionalidade Administrador e Funcionário
- Cadastro de Clientes
- Pedidos, Anotações e Notas não pagas salvas no sistema.


### Aplicação

### Tela de Login (V3.0) 

Adicionado sistema de credenciamento Administrador e Funcionário para a aplicação. 

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/login.png) `versão 3.0`

A aplicação apresenta duas tabelas (TableView), uma para todos os atendimentos adicionados e outra para calcular os valores totais

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170132543.png) `versão 2.0`

Adicionando um atendimento, podemos manipular os valores e a forma de pagameto de acordo com o pedidio que foi feito, sendo mostrado também a situação do pedidoTableView (na coluna **STATUS**) com as opções *ATENDIMENTO* ou *PEDIDO*, a situação muda automaticamento quando agregamos um valor a esse atendimento, reconhecendo que o pedidoTableView fez um pedidoTableView, podemos também excluir um atendimento se necessário preenchendo a coluna " X " e acionando o botão remover.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170513989.png) `versão 2.0`

Nas colunas da tabela é possivel alterar atributos quando necessário, conforme é adicionado atendimentos, a segunda tabela cuida da soma dos valores totais de todos atendimentos, bem como os valores recebidos em cada forma de pagamento quando a coluna **PAGO** é marcada

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170554919.png) `versão 2.0`

De acordo com a marcação das colunas **PAGO** e **ENTREGUE**, a cor da linha da tabela muda, sendo o tom **VERMELHO** para pedidos que foram **ENTREGUES** e **NÃO PAGOS** e **VERDE** quando já foi PAGO, independente de ser entregue ou não, também muda o Status do atendimento de acordo com a marcação das colunas.

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170711789.png) `versão 2.0`

Na parte superior temos os botões "Adicionar" para adicionar um atendimento, "Remover" para remover o atendimento selecionado ~~e "Finalizar" para salvar os registros do dia, 
também tem o campo para o preenchimento da **CHAVE DO PESO** pois nesse caso é necessário a conversão de **REAIS** para **PESOS** por necessidade de receber alguns pagamentos em pesos uruguaios e o campo de preenchimento do **NOME DO FUNCIONÁRIO**, 
com finalidade de apresentar esses dados no momento de finalizar os atendimentos.~~ **V3.0**: Adicionado funcionalidades de ADMINISTRADOS e FUNCIONARIO, cada cargo com suas respectivas permissões. <br>

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/funcionarios.png) `versão 3.0`

### Anotações

Lista com opção para adicionar anotações que serão gravadas no banco de dados, sendo possivel editar e excluir.

### Cadastro de Clientes (V3.0)

Adicionado Funcionalidade de Cadastro de clientes.

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/cliente.png) `versão 3.0`

### Adicinando e editando atendimentos

Ao acionar o botão "Adicionar" entramos na tela para adicionar:

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/add.png) `versão 3.0`

Nessa tela, é pego a informação da **CHAVE DO PESO** inserida no menu Sistema - Chave do peso, podendo ser alterada para aquele pedidoTableView específico quando necessário, sem alterar o valor da chave na tela principal.
Os valores de pesos uruguaios são convertidos automaticamente de acordo com a chave inserida, não sendo possível alterar o campo diretamente na tabela.

<p>Podemos também marcar a situação do atendimento como <strong>PEDIDO</strong> e ele vai entrar na tabela já com o status de <strong>PEDIDO</strong> para indicar que o pedidoTableView fez o pedidoTableView.</p>
<p>Podemos alterar os campos <strong>NOME</strong>, <strong>VALOR EM BRL</strong> e <strong>FORMA DE PAGAMENTO</strong> de acordo com o necessário, lembrando que quando o <strong>PAGO</strong> é marcado, a segunda tabela soma a coluna de acordo com a forma de pagamento selecionada.</p>

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170846017.png) `versão 2.0`

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_170943691.png) `versão 2.0`

![](https://github.com/Beforg/assets/blob/main/imagem_2023-11-20_171020150.png) `versão 2.0`


### Finalizando os atendimentos
Ao acionar o botão **FINALIZAR**, é aberto uma janela para salvar o arquivo gerado em PDF, o nome padrão do arquivo é *caixa_(**informa o dia atual aqui**).pdf* para manter organizado os as informações do caixa<BR>
<pre>V3.0: Adicionado funcionalidade que guarda as notas que não foram pagas (após finalizar o caixa).<BR> OBS: Somente para clientes cadastrados. </pre>

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/pendentes.png) `versão 3.0`

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_182938450.png) `versão 2.0`

Após salvar o documento PDF, esse é o resultado final:

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_183825714.png) `versão 2.0`

É apresentado a tabela de todos atendimentos com a tabela total, sendo reposicionado o status de ATENDENDO por NÃO PEDIU, o PDF vai adicionando mais páginas quando necessário.

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


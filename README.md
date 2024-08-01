# Aplicação de Atendimentos
![](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

[![Generic badge](https://img.shields.io/badge/Versão-3.0.0-<COLOR>.svg)](https://shields.io/)

Projeto em Java com banco de dados PostgreSQL.


| :placard: Vitrine.Dev |  @Beforg   |
| -------------  | --- |
| :sparkles: Nome        | **Atendimentos APP**
| :label: Tecnologias | java, javafx, maven, lombok, postgresql, flyway, css.
| :date: Data da conclusão       | 12 de outubro de 2023
| :back: Download do projeto | [Download do Projeto](https://github.com/Beforg/AtendimentosAPP/releases/download/v3.0.0/atendimentos_setup.exe)
| :balloon: Versão do projeto | Versão 3.0.0 (26 de jul. 2024)

<!-- Inserir imagem com a #vitrinedev ao final do link -->
![](https://github.com/Beforg/assets/blob/main/atendapp3.0/app.png) `versão 3.0`

## Descrição
Aplicação Java desenvolvida para auxiliar nos atendimentos, registrar os atendimentos e pedidos, exibindo a forma de pagamento, valor e marcando se o pedido final foi pago ou entregue, salvando os dados em formato de PDF ao finalizar o horário comercial.

### Detalhes do projeto

#### Versão 3.0:

- Adicionado Banco de Dados (PostgreSQL)
- Funcionalidade Administrador e Funcionário
- Cadastro de Clientes
- Pedidos, Anotações e Notas não pagas salvas no sistema.

## Instrução de Instalação

### Executável:
Executável da [versão 3.0.0](https://github.com/Beforg/AtendimentosAPP/releases/download/v3.0.0/atendimentos_setup.exe) <br>
Ao iniciar a aplicação é necessário fazer a configuração do banco de dados:

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/configbanco.png)

Caminho do banco de dados: <br>

```bash
  postgresql://<host>:<porta>/<database>  
```
Ao salvar ele irá gerar o login: **adm** e senha: **admin** para acessar o sistema.

### Clonando o repositório

```bash
 git clone https://github.com/Beforg/AtendimentosAPP.git
```

## Instruções do Aplicativo

### Tela de Login

Após a configuração de conexão com o banco de dados será gerado o acesso:
- login: **adm**
- senha: **admin**
**É preciso adicionar valor a chave do peso antes de adicionar os atendimentos, através da aba Sistema.**
  
![](https://github.com/Beforg/assets/blob/main/atendapp3.0/login.png)

A aplicação apresenta duas tabelas, uma para todos os atendimentos adicionados e outra para calcular os valores totais. Tudo é salvo no banco de dados.

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/tabelas.png) 

### Cadastro de Clientes

Podemos cadastrar clientes para serem vinculados os pedidos.

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/cliente.png) 

Podemos alterar os valores direto na tabela. As alterações são salvas no banco de dados.

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/tabela.png)


**V3.0**: Adicionado funcionalidades de ADMINISTRADORES e FUNCIONARIOS, cada cargo com suas respectivas permissões. <br>

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/funcionarios.png) `versão 3.0`

### Anotações

Lista com opção para adicionar anotações que serão gravadas no banco de dados, sendo possivel editar e excluir.

### Adicinando e editando atendimentos

Ao acionar o botão "Adicionar" entramos na tela para adicionar:

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/add.png) `versão 3.0`

Nessa tela, é pego a informação da **CHAVE DO PESO** **(inserida no menu Sistema - Chave do peso)**, podendo ser alterada para aquele pedido específico quando necessário, sem alterar o valor da chave na tela principal.
Os valores de pesos uruguaios são convertidos automaticamente de acordo com a chave inserida, não sendo possível alterar o campo diretamente na tabela.


### Finalizando os atendimentos
Ao acionar o botão **FINALIZAR**, é aberto uma janela para salvar o arquivo gerado em PDF, o nome padrão do arquivo é *caixa_(**dia atual**).pdf* para manter organizado os as informações do caixa<BR>
<pre>V3.0: Adicionado funcionalidade que guarda as notas que não foram pagas (após finalizar o caixa).<BR> OBS: Somente para clientes cadastrados. </pre>

![](https://github.com/Beforg/assets/blob/main/atendapp3.0/pendentes.png) `versão 3.0`

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_182938450.png) `versão 2.0`

A quitação de notas pendentes é feita através da busca do código da nota no menu **Relatório**.

Após salvar o documento PDF, esse é o resultado final:

![](https://github.com/Beforg/assets/blob/main/imagem_2023-10-12_183825714.png) `versão 2.0`

É apresentado a tabela de todos atendimentos com a tabela total, sendo reposicionado o status de ATENDENDO por NÃO PEDIU, o PDF vai adicionando mais páginas quando necessário.

## Licensa

[![PyPI license](https://img.shields.io/pypi/l/ansicolortags.svg)](https://github.com/Beforg/AtendimentosAPP/blob/master/LICENSE)

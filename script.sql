create table if not exists anotacoes
(
    id serial primary key,
    titulo       varchar(100) not null,
    descricao    varchar(350) not null,
    data_criacao date         not null,
    data_edicao  date
    );

create table if not exists clientes
(
    id          serial
    primary key,
    nome        varchar(250) not null,
    telefone    varchar(150),
    endereco    varchar(250)
    );

create table if not exists contas
(
    id      serial
    primary key,
    nome    varchar(250) not null,
    tipo    varchar(100) not null,
    senha   varchar(100) not null,
    usuario varchar(100) not null
    );

create table if not exists notas_pendentes
(
    id            serial
    primary key,
    data          date             not null,
    valor         numeric          not null,
    chave_peso    double precision not null,
    cliente_id    integer          not null
    references clientes,
    codigo_pedido uuid
    references pedidos
);

create table if not exists pedidos
(
    codigo          uuid             not null
    primary key,
    nome            varchar(250)     not null,
    status          varchar(100)     not null,
    valor           numeric          not null,
    forma_pagamento varchar(100)     not null,
    entregador      varchar(100)     not null,
    pago            boolean          not null,
    entregue        boolean          not null,
    chave_peso      double precision not null,
    clientes_id     integer
    references clientes,
    data_pedido     date,
    hora_pedido     time
    );
CREATE TABLE IF NOT EXISTS contas (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(250) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    usuario VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(250) NOT NULL,
    telefone VARCHAR(150),
    endereco VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS pedidos (
    codigo BIGINT PRIMARY KEY NOT NULL,
    nome VARCHAR(250) NOT NULL,
    status VARCHAR(100) NOT NULL,
    valor DECIMAL NOT NULL,
    forma_pagamento VARCHAR(100) NOT NULL,
    entregador VARCHAR(100) NOT NULL,
    pago BOOLEAN NOT NULL,
    entregue BOOLEAN NOT NULL,
    chave_peso DOUBLE PRECISION NOT NULL,
    clientes_id INTEGER,
    FOREIGN KEY (clientes_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS notas_pendentes (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    valor DECIMAL NOT NULL,
    chave_peso DOUBLE PRECISION NOT NULL,
    cliente_id INTEGER  NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS anotacoes (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(350) NOT NULL,
    data_criacao DATE NOT NULL,
    data_edicao DATE
);

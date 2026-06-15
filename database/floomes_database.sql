DROP DATABASE IF EXISTS floomes_database;

CREATE DATABASE floomes_database
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE floomes_database;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    perfil VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prev_senhas (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    id_usuario INT NOT NULL,
    prev_senha VARCHAR(255) NOT NULL,
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE empresas (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome_fantasia VARCHAR(255) NOT NULL,
    razao_social VARCHAR(255),
    cnpj VARCHAR(255) UNIQUE,
    telefone VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contatos (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    id_empresa INT,
    temperatura VARCHAR(255),
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_empresa) REFERENCES empresas(id)
);

CREATE TABLE vendas (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    descricao TEXT NOT NULL,
    valor DECIMAL(10, 2),
    status VARCHAR(255) NOT NULL,
    data_fechamento DATETIME,
    id_contato INT,
    id_vendedor_resp INT,
    id_empresa INT,
    data_entrada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_contato) REFERENCES contatos(id),
    FOREIGN KEY (id_vendedor_resp) REFERENCES usuarios(id),
    FOREIGN KEY (id_empresa) REFERENCES empresas(id)
);

CREATE TABLE venda_produtos (
    id_venda INT NOT NULL,
    id_produto INT NOT NULL,

    PRIMARY KEY (id_venda, id_produto),

    FOREIGN KEY (id_venda) REFERENCES vendas(id),
    FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

INSERT INTO usuarios (id, nome, perfil, email, senha) VALUES
(1, 'Felipe Souza', 'administrador', 'felipe@floomes.com', '123'),
(2, 'Ana Lima', 'vendedor', 'ana@floomes.com', '123'),
(3, 'Carlos Mendes', 'vendedor', 'carlos@floomes.com', '123');

INSERT INTO empresas (id, nome_fantasia, razao_social, cnpj, telefone, email) VALUES
(1, 'Tech Solutions', 'Tech Solutions LTDA', '12345678000190', '11999990001', 'contato@techsolutions.com'),
(2, 'Mercado Bom Preco', 'Bom Preco Comercio LTDA', '22345678000191', '11999990002', 'financeiro@bompreco.com'),
(3, 'Clinica Vida', 'Clinica Vida Saude LTDA', '32345678000192', '11999990003', 'atendimento@clinicavida.com');

INSERT INTO produtos (id, nome, descricao) VALUES
(1, 'Sistema CRM', 'Sistema para controle de clientes, contatos e vendas'),
(2, 'Consultoria Comercial', 'Servico de consultoria para melhoria do processo de vendas'),
(3, 'Treinamento de Equipe', 'Treinamento para vendedores e administradores'),
(4, 'Suporte Mensal', 'Plano mensal de suporte tecnico');

INSERT INTO contatos (id, nome, telefone, email, id_empresa, temperatura) VALUES
(1, 'Mariana Rocha', '11988880001', 'mariana@techsolutions.com', 1, 'Quente'),
(2, 'Joao Pereira', '11988880002', 'joao@bompreco.com', 2, 'Morno'),
(3, 'Fernanda Alves', '11988880003', 'fernanda@clinicavida.com', 3, 'Frio');

INSERT INTO vendas (id, descricao, valor, status, data_fechamento, id_contato, id_vendedor_resp, id_empresa) VALUES
(1, 'Venda do sistema CRM para a Tech Solutions', 2500.00, 'GANHA', '2026-06-10 14:30:00', 1, 2, 1),
(2, 'Negociacao de consultoria comercial com Mercado Bom Preco', 1800.00, 'EM_ABERTO', NULL, 2, 3, 2),
(3, 'Treinamento comercial para Clinica Vida', 1200.00, 'PERDIDA', '2026-06-12 10:00:00', 3, 2, 3);

INSERT INTO venda_produtos (id_venda, id_produto) VALUES
(1, 1),
(1, 4),
(2, 2),
(3, 3);

-- INSERTS iniciais para as tabelas solicitante e categoria para não precisar inserir manualmente os dados de teste

INSERT INTO solicitante (nome, cpf_cnpj)
VALUES
    ('João da Silva', '12345678901'),
    ('Maria Oliveira', '98765432100'),
    ('Carlos Santos', '45678912300'),
    ('Empresa Alfa Ltda', '12345678000199'),
    ('Empresa Beta Ltda', '98765432000188');


INSERT INTO categoria (nome)
VALUES
    ('Serviços'),
    ('Material'),
    ('Transporte'),
    ('Alimentacao'),
    ('Equipamentos');
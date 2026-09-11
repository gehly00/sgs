-- Criação das tabelas solicitante, categoria e solicitacao

CREATE TABLE solicitante (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE solicitacao (
    id BIGSERIAL PRIMARY KEY,

    solicitante_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,

    descricao VARCHAR(500) NOT NULL,

    valor NUMERIC(15, 2) NOT NULL,

    data_solicitacao DATE NOT NULL,

    status VARCHAR(20) NOT NULL,

    -- Relacionamentos e restrições
    CONSTRAINT fk_solicitacao_solicitante
        FOREIGN KEY (solicitante_id)
        REFERENCES solicitante(id),

    CONSTRAINT fk_solicitacao_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id),

    CONSTRAINT ck_solicitacao_valor
        CHECK (valor > 0),

    CONSTRAINT ck_solicitacao_status
        CHECK (
            status IN (
                'SOLICITADO',
                'LIBERADO',
                'APROVADO',
                'REJEITADO',
                'CANCELADO'
            )
        )
);
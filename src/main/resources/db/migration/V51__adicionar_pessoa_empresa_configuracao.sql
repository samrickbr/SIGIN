ALTER TABLE configuracoes_sistema
    ADD COLUMN pessoa_empresa_id BIGINT;

ALTER TABLE configuracoes_sistema
    ADD CONSTRAINT fk_configuracao_pessoa_empresa
        FOREIGN KEY (pessoa_empresa_id)
        REFERENCES pessoas(id);
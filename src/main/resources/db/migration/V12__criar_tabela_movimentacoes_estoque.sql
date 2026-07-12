CREATE TABLE movimentacoes_estoque (

    id BIGSERIAL PRIMARY KEY,

    produto_id BIGINT,

    material_id BIGINT,

    local_id BIGINT NOT NULL,

    tipo VARCHAR(50) NOT NULL,

    quantidade NUMERIC(12,3) NOT NULL,

    origem VARCHAR(50) NOT NULL,

    referencia_id BIGINT,

    observacao VARCHAR(500),

    responsavel_id BIGINT,

    data_movimentacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,


    CONSTRAINT fk_mov_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),


    CONSTRAINT fk_mov_material
        FOREIGN KEY (material_id)
        REFERENCES materiais(id),


    CONSTRAINT fk_mov_local
        FOREIGN KEY (local_id)
        REFERENCES locais(id),


    CONSTRAINT fk_mov_responsavel
        FOREIGN KEY (responsavel_id)
        REFERENCES pessoas(id),


    CONSTRAINT ck_mov_produto_material
        CHECK (
            (produto_id IS NOT NULL AND material_id IS NULL)
            OR
            (produto_id IS NULL AND material_id IS NOT NULL)
        )
);
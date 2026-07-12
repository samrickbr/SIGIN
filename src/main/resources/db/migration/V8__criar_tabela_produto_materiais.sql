CREATE TABLE produto_materiais (

    id BIGSERIAL PRIMARY KEY,

    produto_id BIGINT NOT NULL,

    material_id BIGINT NOT NULL,

    quantidade NUMERIC(12,3) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_produto_material_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),

    CONSTRAINT fk_produto_material_material
        FOREIGN KEY (material_id)
        REFERENCES materiais(id),

    CONSTRAINT uk_produto_material
        UNIQUE (produto_id, material_id)
);
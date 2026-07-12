CREATE TABLE op_materiais (

    id BIGSERIAL PRIMARY KEY,

    ordem_producao_id BIGINT NOT NULL,

    material_id BIGINT NOT NULL,

    quantidade_planejada NUMERIC(12,3) NOT NULL,

    quantidade_consumida NUMERIC(12,3) DEFAULT 0,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_op_material_ordem
        FOREIGN KEY (ordem_producao_id)
        REFERENCES ordens_producao(id),

    CONSTRAINT fk_op_material_material
        FOREIGN KEY (material_id)
        REFERENCES materiais(id),

        CONSTRAINT uk_op_material
                UNIQUE (ordem_producao_id, material_id)
);
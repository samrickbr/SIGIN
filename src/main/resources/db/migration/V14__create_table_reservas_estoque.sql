CREATE TABLE reservas_estoque (

    id BIGSERIAL PRIMARY KEY,

    material_id BIGINT NOT NULL,

    ordem_producao_id BIGINT NOT NULL,

    quantidade NUMERIC(19,3) NOT NULL,

    data_reserva TIMESTAMP NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_reserva_material
        FOREIGN KEY (material_id)
        REFERENCES materiais(id),

    CONSTRAINT fk_reserva_op
        FOREIGN KEY (ordem_producao_id)
        REFERENCES ordens_producao(id)
);
ALTER TABLE canais_venda
    ADD COLUMN codigo VARCHAR(30);

DO $$
DECLARE
    quantidade_canais INTEGER;
    quantidade_delivery INTEGER;
    quantidade_loja_fisica INTEGER;
    quantidade_balcao INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO quantidade_canais
    FROM canais_venda;

    SELECT COUNT(*)
    INTO quantidade_delivery
    FROM canais_venda
    WHERE UPPER(TRIM(nome)) = 'DELIVERY';

    SELECT COUNT(*)
    INTO quantidade_loja_fisica
    FROM canais_venda
    WHERE UPPER(TRIM(nome)) IN ('LOJA FISICA', 'LOJA FÍSICA');

    SELECT COUNT(*)
    INTO quantidade_balcao
    FROM canais_venda
    WHERE UPPER(TRIM(nome)) IN ('BALCAO', 'BALCÃO');

    IF quantidade_delivery > 1 THEN
        RAISE EXCEPTION
            'Existem múltiplos canais de venda correspondentes a DELIVERY.';
    END IF;

    IF quantidade_loja_fisica > 1 THEN
        RAISE EXCEPTION
            'Existem múltiplos canais de venda correspondentes a LOJA_FISICA.';
    END IF;

    IF quantidade_balcao > 1 THEN
        RAISE EXCEPTION
            'Existem múltiplos canais de venda correspondentes a BALCAO.';
    END IF;

    IF quantidade_canais = 0 THEN

        INSERT INTO canais_venda (
            id,
            codigo,
            nome,
            descricao,
            ativo
        )
        VALUES
        (
            1,
            'LOJA_FISICA',
            'Loja Física',
            'Canal sistêmico da operação de loja física.',
            TRUE
        ),
        (
            2,
            'DELIVERY',
            'Delivery',
            'Canal sistêmico do módulo Delivery.',
            TRUE
        ),
        (
            3,
            'BALCAO',
            'Balcão',
            'Canal sistêmico do módulo Balcão.',
            TRUE
        );

    ELSE

        IF EXISTS (
            SELECT 1
            FROM canais_venda
            WHERE codigo IS NOT NULL
              AND codigo NOT IN (
                  'LOJA_FISICA',
                  'DELIVERY',
                  'BALCAO'
              )
        ) THEN
            RAISE EXCEPTION
                'Existem canais de venda com código sistêmico inválido.';
        END IF;

        IF quantidade_delivery = 1 THEN
            UPDATE canais_venda
            SET codigo = 'DELIVERY'
            WHERE UPPER(TRIM(nome)) = 'DELIVERY';
        END IF;

        IF quantidade_loja_fisica = 1 THEN
            UPDATE canais_venda
            SET codigo = 'LOJA_FISICA'
            WHERE UPPER(TRIM(nome)) IN ('LOJA FISICA', 'LOJA FÍSICA');
        END IF;

        IF quantidade_balcao = 1 THEN
            UPDATE canais_venda
            SET codigo = 'BALCAO'
            WHERE UPPER(TRIM(nome)) IN ('BALCAO', 'BALCÃO');
        END IF;

        IF EXISTS (
            SELECT 1
            FROM canais_venda
            WHERE codigo IS NULL
        ) THEN
            RAISE EXCEPTION
                'Existem canais de venda antigos não reconhecidos. Migração interrompida para preservar os dados.';
        END IF;

        IF quantidade_delivery = 0 THEN
            INSERT INTO canais_venda (
                codigo,
                nome,
                descricao,
                ativo
            )
            VALUES (
                'DELIVERY',
                'Delivery',
                'Canal sistêmico do módulo Delivery.',
                TRUE
            );
        END IF;

        IF quantidade_loja_fisica = 0 THEN
            INSERT INTO canais_venda (
                codigo,
                nome,
                descricao,
                ativo
            )
            VALUES (
                'LOJA_FISICA',
                'Loja Física',
                'Canal sistêmico da operação de loja física.',
                TRUE
            );
        END IF;

        IF quantidade_balcao = 0 THEN
            INSERT INTO canais_venda (
                codigo,
                nome,
                descricao,
                ativo
            )
            VALUES (
                'BALCAO',
                'Balcão',
                'Canal sistêmico do módulo Balcão.',
                TRUE
            );
        END IF;

    END IF;
END $$;

ALTER TABLE canais_venda
    ALTER COLUMN codigo SET NOT NULL;

ALTER TABLE canais_venda
    ADD CONSTRAINT uk_canais_venda_codigo UNIQUE (codigo);

SELECT setval(
    'canais_venda_id_seq',
    COALESCE((SELECT MAX(id) FROM canais_venda), 1),
    true
);

INSERT INTO tipos_pessoa (
    nome,
    descricao,
    ativo
)
SELECT
    'CLIENTE',
    'Pessoa que compra produtos',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM tipos_pessoa
    WHERE UPPER(TRIM(nome)) = 'CLIENTE'
);
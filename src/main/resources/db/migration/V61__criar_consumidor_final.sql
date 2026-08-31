INSERT INTO tipos_pessoa (nome)
SELECT 'CONSUMIDOR_FINAL'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipos_pessoa
    WHERE nome = 'CONSUMIDOR_FINAL'
);

INSERT INTO pessoas (nome, documento)
SELECT 'CONSUMIDOR FINAL', NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM pessoas
    WHERE nome = 'CONSUMIDOR FINAL'
      AND documento IS NULL
);

INSERT INTO pessoa_tipos (pessoa_id, tipo_pessoa_id)
SELECT
    p.id,
    t.id
FROM pessoas p
JOIN tipos_pessoa t
    ON t.nome = 'CONSUMIDOR_FINAL'
WHERE p.nome = 'CONSUMIDOR FINAL'
  AND p.documento IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM pessoa_tipos pt
      WHERE pt.pessoa_id = p.id
        AND pt.tipo_pessoa_id = t.id
  );
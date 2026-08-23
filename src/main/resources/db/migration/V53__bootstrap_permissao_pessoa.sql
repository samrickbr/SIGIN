-- V53__bootstrap_permissao_pessoa.sql

-- Permissão sistêmica para gerenciamento administrativo de Pessoas
INSERT INTO permissoes (
    codigo,
    descricao,
    ativo
)
SELECT
    'PESSOA',
    'Permite gerenciar Pessoas',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM permissoes
    WHERE codigo = 'PESSOA'
);

-- Perfil Administrador recebe a permissão de Pessoa
INSERT INTO perfil_permissoes (
    perfil_id,
    permissao_id
)
SELECT
    perfil.id,
    permissao.id
FROM perfis perfil
CROSS JOIN permissoes permissao
WHERE perfil.nome = 'Administrador'
  AND permissao.codigo = 'PESSOA'
  AND NOT EXISTS (
      SELECT 1
      FROM perfil_permissoes pp
      WHERE pp.perfil_id = perfil.id
        AND pp.permissao_id = permissao.id
  );
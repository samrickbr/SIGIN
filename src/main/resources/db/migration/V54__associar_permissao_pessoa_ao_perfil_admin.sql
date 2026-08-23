-- V54__associar_permissao_pessoa_ao_perfil_admin.sql

-- Perfil ADMIN recebe a permissão sistêmica de gerenciamento de Pessoas
INSERT INTO perfil_permissoes (
    perfil_id,
    permissao_id
)
SELECT
    perfil.id,
    permissao.id
FROM perfis perfil
CROSS JOIN permissoes permissao
WHERE perfil.nome = 'ADMIN'
  AND permissao.codigo = 'PESSOA'
  AND NOT EXISTS (
      SELECT 1
      FROM perfil_permissoes pp
      WHERE pp.perfil_id = perfil.id
        AND pp.permissao_id = permissao.id
  );
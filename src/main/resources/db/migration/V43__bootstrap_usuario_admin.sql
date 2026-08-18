-- V43__bootstrap_usuario_admin.sql

-- Pessoa utilizada pela credencial administrativa inicial
INSERT INTO pessoas (
    nome,
    ativo,
    data_criacao
)
SELECT
    'Administrador',
    TRUE,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM pessoas
    WHERE nome = 'Administrador'
);

-- Perfil administrativo inicial
INSERT INTO perfis (
    nome,
    descricao,
    ativo
)
SELECT
    'Administrador',
    'Administrador do sistema',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM perfis
    WHERE nome = 'Administrador'
);

-- Permissão já utilizada pelo Core
INSERT INTO permissoes (
    codigo,
    descricao,
    ativo
)
SELECT
    'PRODUTO_VISUALIZAR',
    'Permite visualizar produtos',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM permissoes
    WHERE codigo = 'PRODUTO_VISUALIZAR'
);

-- Perfil Administrador recebe a permissão
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
  AND permissao.codigo = 'PRODUTO_VISUALIZAR'
  AND NOT EXISTS (
      SELECT 1
      FROM perfil_permissoes pp
      WHERE pp.perfil_id = perfil.id
        AND pp.permissao_id = permissao.id
  );

-- Credencial administrativa inicial
-- Login: admin
-- Senha inicial: admin
-- Hash gerado pelo BCryptPasswordEncoder do próprio projeto.
INSERT INTO usuarios (
    pessoa_id,
    login,
    senha,
    ativo,
    data_criacao
)
SELECT
    pessoa.id,
    'admin',
    '$2a$10$Ukd/eqRm/8kH0tsotp2Bq.ib/Kd.LEJQ.6nyyZfYqPBDh8TzoelUW',
    TRUE,
    CURRENT_TIMESTAMP
FROM pessoas pessoa
WHERE pessoa.nome = 'Administrador'
  AND NOT EXISTS (
      SELECT 1
      FROM usuarios
      WHERE login = 'admin'
  );

-- Usuário Administrador recebe o perfil Administrador
INSERT INTO usuarios_perfis (
    usuario_id,
    perfil_id
)
SELECT
    usuario.id,
    perfil.id
FROM usuarios usuario
CROSS JOIN perfis perfil
WHERE usuario.login = 'admin'
  AND perfil.nome = 'Administrador'
  AND NOT EXISTS (
      SELECT 1
      FROM usuarios_perfis up
      WHERE up.usuario_id = usuario.id
        AND up.perfil_id = perfil.id
  );
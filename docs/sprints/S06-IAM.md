# Sprint 06 — Usuario IAM, JWT e Controle de Acesso

## Objetivo

Implementar a camada de identidade e acesso do SIGIN Core.

## Entregas confirmadas

- módulo Usuario;
- integração Usuario com Pessoa;
- cadastro e controle de usuário;
- login;
- senha protegida com BCrypt;
- JWT;
- JwtAuthenticationFilter;
- CustomUserDetailsService;
- SecurityContext;
- authorities carregadas dinamicamente;
- proteção de endpoints com Spring Security;
- Perfis;
- Permissões;
- UsuarioPerfil;
- PerfilPermissao.

## Modelo de autorização

Pessoa
Usuario
UsuarioPerfil
Perfil
PerfilPermissao
Permissao

Permissões não são atribuídas diretamente ao Usuario.

O Usuario recebe permissões através dos Perfis associados.

## Segurança

O fluxo implementado utiliza:

Login
JWT
Filtro de autenticação
SecurityContext
Usuário autenticado
Authorities
Proteção dos endpoints

A autorização pode ser aplicada através das authorities associadas ao usuário autenticado e da proteção por método existente no Core.

## Histórico Git

Commit principal identificado:

83d46e803f66a02d4212073542ef9577eca79c32

Mensagem:

feat: implement user identity and jwt authorization

A referência de versão v0.6.0 não foi confirmada como tag Git e, portanto, não é registrada como tag desta Sprint.

## Validação

O histórico existente registra validações de autenticação e autorização, incluindo comportamento de endpoints protegidos com e sem autenticação e com permissões diferentes.

Somente validações efetivamente registradas no histórico são consideradas nesta documentação.

## Resultado

Concluída.

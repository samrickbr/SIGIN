# Feedback de Execução — Usuario IAM

## Status

CONCLUÍDO

## Escopo executado

Implementação da camada de identidade e autorização do SIGIN Core.

## Alterações

Foram implementados os componentes necessários para:

- Usuario;
- autenticação;
- login;
- senha protegida;
- JWT;
- filtro JWT;
- UserDetailsService;
- SecurityContext;
- authorities;
- Perfis;
- Permissões;
- UsuarioPerfil;
- PerfilPermissao;
- integração Usuario com Pessoa.

## Contratos

O Core passou a disponibilizar o fluxo de autenticação baseado em login e JWT e a proteção dos endpoints através do mecanismo de segurança existente.

A evolução posterior do contrato de identidade autenticada foi registrada separadamente em EXEC-AUTH-ME.md.

## Segurança

O mecanismo utiliza JWT e Spring Security.

As permissões são derivadas da estrutura:

Usuario
UsuarioPerfil
Perfil
PerfilPermissao
Permissao

Não existe atribuição direta de Permissao ao Usuario.

## Testes e validação

Foram registradas no histórico validações de autenticação e autorização, incluindo acesso a endpoints protegidos e comportamento conforme as authorities disponíveis.

## Commit

83d46e803f66a02d4212073542ef9577eca79c32

Mensagem:

feat: implement user identity and jwt authorization

## Branch

main

## Push

O commit faz parte do histórico consolidado do repositório.

## Observações

O IAM permanece como infraestrutura do Core e não deve depender dos domínios comerciais.

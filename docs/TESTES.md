# Fluxos Oficiais de Teste

## Fluxo Pedido → Produção

1 Criar Pedido

↓

2 Adicionar Item

↓

3 Atualizar Valor

↓

4 Gerar Ordem Produção

↓

5 Gerar Materiais

↓

6 Consultar Materiais

Resultado esperado:

Pedido criado

OP criada

Materiais gerados automaticamente

---

## Objetivo

Todos estes passos deverão ser executados automaticamente pela Collection do Postman.

Nenhum desenvolvedor deverá executar estes testes manualmente.

## Validação do contrato `/auth/me`

O contrato `GET /auth/me` foi validado manualmente através do Swagger.

### Cenários executados

1. Requisição autenticada com JWT válido:

    * resultado: `200 OK`;
    * identidade do usuário retornada corretamente;
    * pessoa vinculada retornada;
    * perfis retornados;
    * permissões retornadas.

2. Requisição sem token:

    * resultado: `403 Forbidden`.

3. Requisição com token inválido:

    * resultado: `403 Forbidden`.

### Status

**Validado manualmente pelo Swagger.**

Não foi adicionada uma suíte específica de testes automatizados para `/auth/me` nesta etapa. A cobertura automatizada existente permanece concentrada nos testes de regras de negócio e fluxos críticos do Core.

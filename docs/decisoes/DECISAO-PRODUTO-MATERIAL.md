# Decisão Arquitetural — Produto × Material e Reativação de Vínculos

## Status

**CONSOLIDADA — F08**

## 1. Contexto

`ProdutoMaterial` representa o vínculo persistente entre um Produto e um Material.

O vínculo possui controle de situação por meio do campo `ativo` e possui unicidade definida para a combinação:

- `produto_id`;
- `material_id`.

A F08 consolidou o comportamento desse vínculo quando ele é inativado e posteriormente incluído novamente.

## 2. DELETE

O endpoint:

`DELETE /produto-materiais/{id}`

representa **soft delete**.

O comportamento é:

`ativo = false`

O registro não é removido fisicamente.

Não deve ser criado endpoint alternativo para substituir essa operação.

## 3. Reutilização de vínculo inativo

Quando já existir um vínculo:

- Produto X;
- Material Y;
- `ativo = false`;

e ocorrer uma nova solicitação de criação para o mesmo Produto × Material, o Core deve reutilizar o registro existente.

O fluxo deve:

1. localizar o vínculo existente;
2. reativá-lo;
3. atualizar a quantidade recebida;
4. preservar o ID;
5. salvar o mesmo registro;
6. retornar o vínculo reativado.

Não deve ser criada uma segunda linha.

## 4. Duplicidade de vínculo ativo

Quando o vínculo já existir com:

`ativo = true`

uma nova criação para o mesmo Produto × Material deve ser rejeitada como duplicidade.

## 5. Unicidade

A constraint:

`uk_produto_material`

sobre:

- `produto_id`;
- `material_id`;

deve permanecer.

A unicidade do vínculo não deve ser removida ou enfraquecida para permitir a criação de novos registros enquanto existir um vínculo inativo.

## 6. Princípio consolidado

Para `ProdutoMaterial`, fica consolidado o princípio:

**Soft delete + unicidade + reutilização do vínculo existente.**

A existência de um vínculo inativo não permite a criação de uma segunda linha para a mesma combinação Produto × Material.

## 7. Escopo da decisão

Esta decisão é específica de `ProdutoMaterial`.

Ela **não deve ser generalizada automaticamente para todas as entidades do SIGIN**.

Casos envolvendo outras entidades devem ser avaliados pelo Roadmap Core antes de receber comportamento equivalente.

## 8. Temas relacionados

Permanecem como assuntos de Roadmap:

- política geral de reativação;
- comportamento de unicidade diante de soft delete;
- tratamento administrativo de registros inativos;
- permissões relacionadas à reativação;
- critérios para reutilização ou criação de novos registros.

Esses temas não fazem parte desta decisão e não foram implementados na F08.
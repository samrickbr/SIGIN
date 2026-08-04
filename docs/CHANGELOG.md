# Changelog

## [0.4.0] - 2026-07-09

### Adicionado

- Cadastro de locais
- CRUD de locais
- Exclusão lógica
- Normalização de nomes
- Utilitário StringUtil
- Melhorias na documentação

### Estrutura

- Produto
- Categoria
- Local
- Tratamentos iniciais de exceção

## Sprint 06

- Criado módulo de materiais
- Implementado padrão Mapper
- Criado CRUD de materiais

## Sprint 08

- Criado módulo de Ordem de Produção
- Implementado controle inicial de produção
- Criado CRUD de OP

## Sprint 09

- Criado controle de materiais da Ordem de Produção
- Implementado vínculo OP x Material
- Criado CRUD de consumo planejado

## Sprint 10

- Criado módulo de apontamento de produção
- Implementado controle de produção realizada
- Implementado registro de perdas
- Atualização automática da OP

## Sprint 11

- Criado módulo de movimentação de estoque
- Implementado registro de entradas de materiais
- Implementado registro de produtos acabados
- Criado histórico de movimentações

## Sprint 12

- Criada consulta de saldo de estoque
- Implementado cálculo de estoque baseado em movimentações

## Sprint 13

* Adicionado controle de ENTRADA e SAIDA nas movimentações de estoque
* Implementado cálculo de saldo considerando movimentações de saída
* Automatizado o consumo de materiais durante o apontamento de produção
* Integrada a estrutura do produto (BOM) ao estoque

## [Sprint 14] - 2026-07-13

### Added
- Implementado módulo de Reserva de Estoque.
- Criada entidade ReservaEstoque.
- Implementado cálculo de estoque disponível.
- Implementada consulta de quantidade reservada por material.
- Implementada reserva automática de materiais na criação da Ordem de Produção.
- Validação de disponibilidade de estoque antes da reserva.
- Integração entre Ordem de Produção e Reserva de Estoque.

### Changed
- Estoque passa a considerar materiais reservados no cálculo de disponibilidade.
- Ordem de Produção passa a iniciar o planejamento de materiais automaticamente.

### Fixed
- Garantido rollback transacional caso a reserva de materiais falhe.

# Sprint 15

## Added

- Implementado ciclo de vida da Ordem de Produção.
- Adicionado enum `StatusOrdemProducao`.
- Criados endpoints para mudança de status da OP.
- Implementada reserva automática de materiais.
- Adicionadas validações de transição de status.
- Registro automático de `dataInicio` e `dataFim`.
- Correção de `LazyInitializationException` nos endpoints de status.
- Integração inicial da Collection Postman para testes automatizados.

## Changed

- Ordem de Produção passou a utilizar enum para controle de status.
- Refatoração do `OrdemProducaoService`.
- Refatoração do `OrdemProducaoMapper`.

# Sprint 16

## Adicionado

- Módulo de Pedidos
- Módulo de Itens do Pedido
- Enum StatusPedido
- Cálculo automático do valor total
- Relacionamento Pedido x Cliente
- Relacionamento Pedido x Produto
- Preparação estrutural para integração com Produção

## Última evolução

Sprint 20 concluída.

O SIGIN possui agora um fluxo DEV automatizado para validar o processo:

Pedido → Produção → Materiais.

Essa estrutura reduz a necessidade de testes manuais durante o desenvolvimento.

# Changelog SIGIN

Todas as alterações relevantes do projeto serão documentadas neste arquivo.

---

# [0.5.0] — Sprint 04 — Fundação Comercial (Core)

## Adicionado

### Canal de Venda

* Criado módulo `CanalVenda`.
* Implementado cadastro de canais de venda.
* Criados endpoints CRUD.
* Adicionada estrutura para múltiplos canais comerciais.

### Produto x Canal

* Criado relacionamento `ProdutoCanal`.
* Implementada disponibilidade de produtos por canal.
* Criadas validações de duplicidade Produto x Canal.
* Adicionada validação de produto disponível antes da venda.

### Produto Comercial

* Evoluído módulo `ProdutoVenda`.
* Adicionado relacionamento com canal de venda.
* Permitido controle de preço por canal.
* Permitido controle de disponibilidade comercial por canal.
* Atualizados DTOs, Mapper, Repository e Service.

### Pedidos

* Adicionado canal de origem no pedido.
* Pedido agora registra o canal responsável pela venda.
* Removida dependência de preço informado pelo cliente.
* Valor dos itens passa a ser obtido automaticamente pelo Core.
* Valor total do pedido passa a ser calculado pelo sistema.

---

## Alterado

### Regras Comerciais

Antes:

```text
Cliente/Frontend
        |
        └── Enviava preço do produto
```

Depois:

```text
Pedido
   |
   ▼
ProdutoVendaService
   |
   ├── Valida ProdutoCanal
   └── Busca preço comercial
```

---

## Refatorado

* Centralizada regra comercial no `ProdutoVendaService`.
* Centralizada validação de disponibilidade no `ProdutoCanalService`.
* Removidas regras duplicadas dos serviços de Pedido.
* Preparada arquitetura para múltiplos canais.

---

## Banco de Dados

Novas migrations:

* `V26__create_table_canal_venda.sql`
* `V27__create_table_produto_canal.sql`
* `V28__alter_produto_venda_add_canal.sql`
* `V29__add_canal_origem_pedidos.sql`

---

## Documentação

Atualizado:

* Integração Delivery.
* Roadmap de Sprints.
* Tasks Sprint 04.
* Documentação de Seeders.

---

# Impacto da versão

O Core Comercial do SIGIN passa a controlar:

* onde um produto pode ser vendido;
* por qual canal;
* qual preço deve ser aplicado;
* se o produto está disponível;
* origem dos pedidos.

A arquitetura passa a suportar expansão para:

* Delivery;
* PDV;
* Marketplace;
* WhatsApp;
* E-commerce;
* API Pública.

---

# Próximas etapas

Sprint 05 será definida no Roadmap do projeto.

## v0.6.0 - Sprint 05

### Added

- Implementado módulo financeiro inicial.
- Criada estrutura de contas a receber.
- Criado controle de movimentação de caixa.
- Integrado faturamento de pedidos ao financeiro.
- Implementada baixa automática por forma de pagamento.
- Criado registro de entradas no caixa originadas por pedidos.

### Changed

- Pedidos passaram a exigir canal de venda.
- Pedidos passaram a exigir forma de pagamento.
- Criado saneamento de dados legados via migration.

### Technical

- Novas entidades:
    - ContaReceber
    - CaixaMovimento

- Novos serviços:
    - FinanceiroPedidoService

- Novas migrations financeiras.
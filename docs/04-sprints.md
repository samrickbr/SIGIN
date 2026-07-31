# Roadmap de Sprints

---

# Sprint 01 — Fundação do Core

**Status:** ✅ Concluída

### Objetivos

* Estrutura inicial do projeto
* Cadastro de Produtos
* Categorias
* Pessoas
* Materiais
* Locais
* Configurações iniciais
* Base do estoque

---

# Sprint 02 — Produção

**Status:** ✅ Concluída

### Objetivos

* Ordem de Produção
* OP Materiais
* Apontamentos
* Consumo automático
* Reserva de estoque
* Movimentações

---

# Sprint 03 — Comercial Inicial

**Status:** ✅ Concluída

### Objetivos

* Pedidos
* Pedido Itens
* Compras
* Configurações
* Produto Comercial
* Carrinho
* Integração inicial Delivery

---

# Sprint 04 — Fundação Comercial (Core)

**Status:** ✅ Concluída

## Objetivo

Preparar o Core Comercial para suportar múltiplos canais de venda, centralizando toda a lógica comercial do sistema.

## Entregas

### Comercial

* Cadastro de Canais de Venda
* Produto x Canal
* Produto Comercial por Canal
* Pedido com Canal de Origem

### Core

* Preço obtido automaticamente pelo Core
* Remoção do envio de preço pelo cliente
* Centralização das regras comerciais
* Disponibilidade por Canal
* Preparação para múltiplos canais

### Arquitetura

* Criação do módulo CanalVenda
* Criação do módulo ProdutoCanal
* Evolução do ProdutoVenda
* Refatoração das regras comerciais
* Preparação para Delivery, PDV, Marketplace e APIs

## Resultado

O Core passou a ser responsável por todas as regras comerciais do sistema.

Os módulos consumidores deixam de conhecer regras de preço, disponibilidade e validações comerciais, passando apenas a consumir os serviços do Core.

---

# Sprint 05

**Status:** ⏳ Planejada

A definir no Roadmap.

---

# Evolução do Core

| Sprint    | Status | Principal entrega  |
| --------- | :----: | ------------------ |
| Sprint 01 |    ✅   | Fundação do Core   |
| Sprint 02 |    ✅   | Produção           |
| Sprint 03 |    ✅   | Comercial Inicial  |
| Sprint 04 |    ✅   | Fundação Comercial |
| Sprint 05 |    ⏳   | Planejamento       |

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

## Sprint 05 — Financeiro Inicial (v0.6.0)

Status: ✅ Concluída

Objetivo:
Criar a base financeira integrada ao fluxo comercial do Core.

Entregas:

- Contas a Receber
- Caixa (movimentações financeiras)
- Integração Pedido x Financeiro
- Faturamento de pedidos
- Baixa automática conforme forma de pagamento

Decisões arquiteturais:

- O Pedido continua sendo responsável pelo fluxo comercial.
- O Financeiro recebe eventos do Pedido através de serviços de integração.
- Faturamento é o momento em que o financeiro é gerado.
- Forma de pagamento define comportamento de baixa automática.
- Caixa registra entradas originadas de vendas.

Implementações:

✅ ContaReceber
✅ CaixaMovimento
✅ StatusContaReceber
✅ TipoMovimentoCaixa
✅ OrigemMovimentoCaixa
✅ FinanceiroPedidoService
✅ Endpoint POST /api/pedidos/{id}/faturar

Validações realizadas:

- Pedido faturado corretamente.
- Conta a receber criada.
- PIX com baixa automática gera movimento no caixa.
- Movimento de caixa vinculado ao pedido através de referencia_id.

Migration:

V36__ajustar_pedidos_legados.sql

Ajustes:

- Preenchimento de canal de venda em pedidos antigos.
- Preenchimento de forma de pagamento em pedidos antigos.
- Aplicação de NOT NULL após saneamento dos dados.
---

# Evolução do Core

| Sprint    | Status | Principal entrega        |
|-----------|:------:|--------------------------|
| Sprint 01 |   ✅   | Fundação do Core         |
| Sprint 02 |   ✅   | Produção                 |
| Sprint 03 |   ✅   | Comercial Inicial        |
| Sprint 04 |   ✅   | Fundação Comercial       |
| Sprint 05 |   ✅   | Financeiro Inicial       |
| Sprint 06 |   🔜   | Refatoração Delivery     |
| Sprint 07 |   ⏳   | PDV                      |
| Sprint 08 |   ⏳   | Comanda                  |
| Sprint 09 |   ⏳   | Financeiro Avançado      |
| Sprint 10 |   ⏳   | Usuários e Permissões    |


---

definido :

Sprint 06 — Integração Delivery com Core

Objetivo:
Remover regras comerciais duplicadas do Delivery e utilizar o Core como fonte oficial de produtos, pedidos e produção.

Hoje:

Delivery-back possui:

produto
setor
pedido
fluxo

Futuro:

Delivery:

Cliente
|
Carrinho
|
Core API
|
Pedido Core

Delivery vira uma camada de experiência.

Sprint 07 — PDV

Aqui eu concordo com sua observação:

Precisamos pensar antes.

Eu não faria:

"balcão = delivery"

Eu criaria:

Módulo PDV

Com:

abertura de caixa
operador
venda rápida
impressão
pagamento
fechamento

Mas usando:

Pedido Core

como motor.

Sprint 08 — Comanda

Comanda provavelmente será outro canal:

CANAL_COMANDA

Fluxo:

Mesa
↓
Comanda aberta
↓
Itens adicionados
↓
Pedido
↓
Produção
↓
Pagamento

Sprint 09 — Financeiro Avançado

contas a pagar
fornecedores
despesas
fechamento de caixa
relatórios
conciliação

Sprint 10 — Usuários e Permissões

Porque agora teremos:

cozinha
pizzaria
lanchonete
PDV
administração
cliente
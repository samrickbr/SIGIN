# SIGIN — Controle de Sprints

## Sprint 01 — Estrutura inicial
Status: ✅ Concluída

## Sprint 02 — Produtos e Categorias
Status: ✅ Concluída

## Sprint 03 — Padronização e melhorias
Status: ✅ Concluída

## Sprint 04 — Cadastro de Locais
Status: ✅ Concluída

Entregas:
- Cadastro de locais
- CRUD completo
- Exclusão lógica
- Padronização de nomes
- StringUtil
- Regras iniciais de qualidade de dados

---

# Sprint Atual

## Sprint 05 — Cadastros auxiliares

Status: ⏳ Planejamento

Objetivo:

Criar os cadastros base necessários para suportar clientes, fornecedores, impressoras e materiais.

---
## Sprint 06 - Cadastro de Materiais

Status: Concluída

Implementado:
- Cadastro de materiais
- Controle de unidade de medida
- Estoque mínimo inicial
- CRUD completo
- Novo padrão com Mapper

## Sprint 07 - Produto x Material

Status: Concluída

Implementado:

* Cadastro do vínculo entre produto e material
* Definição da composição do produto (BOM)
* Controle da quantidade de material utilizada por unidade produzida
* Validação de materiais duplicados no mesmo produto
* CRUD completo do relacionamento Produto x Material

Relacionamentos:

* Um produto pode possuir vários materiais
* Um material pode estar vinculado a vários produtos
* A quantidade representa o consumo necessário para produzir uma unidade do produto

## Sprint 08 - Ordem de Produção

Status: Concluída

Implementado:
- Cadastro de ordens de produção
- Controle de produto produzido
- Local destino
- Responsável
- Origem da produção
- Controle de status inicial
- CRUD completo

## Sprint 09 - Consumo de Materiais da OP

Status: Concluída

Implementado:
- Cadastro de materiais vinculados à OP
- Controle de quantidade planejada
- Controle de quantidade consumida
- Relacionamento OP x Material
- CRUD completo

## Sprint 10 - Execução da Produção

Status: Concluída

Implementado:
- Apontamento de produção
- Registro de perdas
- Histórico de produção
- Atualização da quantidade produzida na OP
- CRUD completo

## Sprint 11 - Movimentação de Estoque

Status: Concluída

Implementado:
- Cadastro de movimentações de estoque
- Entrada de materiais
- Entrada de produtos acabados
- Controle de origem da movimentação
- Vinculação com local e responsável
- Rastreabilidade por referência
- Validação produto x material

## Sprint 12 - Consulta de Estoque

Status: Concluída

Implementado:
- Consulta de saldo de estoque
- Cálculo baseado em movimentações
- Consulta consolidada de produtos e materiais

## Sprint 13 - Consumo Automático de Materiais

Status: Concluída

Objetivo:
Automatizar o consumo de matérias-primas durante o apontamento da produção.

Implementado:

* Controle de movimentações de entrada e saída
* Campo `movimento` (ENTRADA/SAIDA)
* Ajuste no cálculo do saldo de estoque
* Consumo automático baseado na composição do produto (BOM)
* Geração automática de movimentações de consumo ao registrar um apontamento de produção

Fluxo:

Apontamento de Produção
→ Busca composição do produto
→ Calcula consumo de cada material
→ Gera movimentações de saída
→ Atualiza automaticamente o saldo do estoque

## Sprint 14 — Reserva de Estoque

Status: ✅ Concluída

### Objetivo
Implementar o planejamento de materiais através da reserva automática de estoque no momento da criação da Ordem de Produção.

### Entregas
- Cadastro de reservas.
- Cálculo de estoque disponível.
- Validação de disponibilidade.
- Reserva automática.
- Rollback transacional.

Resultado:
A Ordem de Produção passa a garantir a disponibilidade de materiais antes do início da fabricação.

# Sprint 15 — Workflow da Ordem de Produção

## Objetivo

Implementar o ciclo completo de vida de uma Ordem de Produção, garantindo transições válidas de status e integração com a reserva de estoque.

## Entregas

- Enum `StatusOrdemProducao`
- Workflow da OP
- Reserva automática de materiais
- Endpoints de mudança de status
- Datas automáticas de início e fim
- Validações de regras de negócio
- Refatoração do service e mapper

# Sprint 16 — Pedidos Comerciais

**Status:** ✅ Concluída

## Objetivo

Implementar o módulo comercial de pedidos, preparando a integração entre vendas e produção.

## Entregas

- Cadastro de Pedidos
- CRUD completo de Pedidos
- Status do Pedido
- Exclusão lógica
- Cadastro de Itens do Pedido
- Cálculo automático do valor total do pedido
- Relacionamento Pedido x Cliente
- Relacionamento Pedido x Produto
- Estrutura preparada para integração com Ordem de Produção
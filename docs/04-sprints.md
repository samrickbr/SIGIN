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

# Sprint 17 — Integração Pedido e Produção

**Status:** ✅ Concluída

## Objetivo

Criar o primeiro fluxo integrado entre módulo comercial e módulo de produção.

## Entregas

- Numeração automática de pedidos
- Numeração automática de ordens de produção
- Configuração centralizada de sequências
- Geração de OP a partir de pedido
- Criação automática de OP por item do pedido
- Atualização automática do status do pedido
- Integração Pedido → Ordem de Produção

## Fluxo implementado

Pedido
↓
PedidoItem
↓
Ordem de Produção
↓
Produção

## Regras

- Um pedido pode gerar várias ordens de produção.
- A OP recebe origem PEDIDO.
- Pedidos gerados para produção ficam como AGUARDANDO_PRODUCAO.

# SIGIN - Sprint 18

## Objetivo

Integrar completamente o fluxo entre Pedidos e Produção através da ficha técnica do produto.

---

# Funcionalidades implementadas

## Pedido

- Cadastro de pedidos
- Cancelamento de pedidos
- Geração automática do número do pedido
- Atualização automática do valor total
- Integração com itens

---

## PedidoItem

- Inclusão de itens
- Cálculo automático do valor total
- Atualização do pedido após alterações

---

## Ordem de Produção

Implementada geração automática da OP através do pedido.

Fluxo:

Pedido
→ Item
→ Ordem de Produção

---

## Ficha Técnica

Criada integração entre:

Produto
→ ProdutoMaterial

Cada produto possui sua ficha técnica de materiais.

---

## Explosão da Ficha Técnica

Ao gerar uma Ordem de Produção o sistema:

- localiza a ficha técnica
- calcula consumo
- cria automaticamente os materiais da OP

Fluxo:

Pedido

↓

Ordem Produção

↓

ProdutoMaterial

↓

OpMaterial

---

## OpMaterial

Novo módulo responsável por armazenar:

- material utilizado
- quantidade planejada
- quantidade consumida

Preparado para futuras baixas de estoque.

---

## Validações

Implementadas:

- Produto sem ficha técnica
- Produto inativo
- Correção da geração sequencial de números

---

# Fluxo validado

Pedido

↓

Adicionar Item

↓

Calcular Valor

↓

Gerar OP

↓

Gerar Materiais

↓

Consultar Materiais

Todos os testes executados com sucesso.

---

# Status

Sprint concluída

✅ Pedido

✅ PedidoItem

✅ Ordem Produção

✅ ProdutoMaterial

✅ OpMaterial

✅ Integração Pedido → Produção

# Sprint 19 - Testes e preparação de automação

## Objetivo
Criar uma base inicial para testes automatizados e melhorar a validação dos fluxos principais do SIGIN.

## Entregas

- Configuração da estrutura de testes com Spring Boot Test.
- Validação inicial do contexto da aplicação.
- Criação da base para testes de integração.
- Identificação da necessidade de automatização dos fluxos completos do sistema.

## Observações

Durante a validação foi identificado que testes manuais estavam consumindo muito tempo devido à quantidade de etapas necessárias para validar pedidos, produção e materiais.

A estratégia foi direcionada para criação de ferramentas DEV para simulação dos fluxos.

# Sprint 20 - Fluxo DEV automatizado de Pedido e Produção

## Objetivo
Criar uma ferramenta interna para executar o fluxo completo de pedido até produção sem necessidade de múltiplos testes manuais.

## Entregas

### Novo módulo Fluxo

Criado:

- FluxoPedidoService
- FluxoPedidoResponse

Responsável por orquestrar:

Pedido → Item → Ordem de Produção → OP Materiais

---

### Endpoint DEV

Criado:

POST `/dev/fluxo/pedido`

Executa automaticamente:

1. Criação de pedido teste.
2. Inclusão de item.
3. Geração da Ordem de Produção.
4. Geração automática dos materiais necessários.

---

### Validações realizadas

Fluxo validado com sucesso:

Pedido criado:
- ID: 14

Ordem de Produção:
- ID: 11
- Número: OP000009

Material gerado:

- Material: PLA Preto
- Quantidade planejada: 0.8 KG

---

## Ajustes realizados

- Inicialização da lista de itens da entidade Pedido.
- Correção do fluxo de persistência entre Pedido e PedidoItem.
- Melhoria da ferramenta DEV para testes rápidos.

## Resultado

Fluxo principal de venda até produção automatizado com sucesso.

# Sprint 21 - Fluxo DEV de Produção e Estoque

## Objetivo

Automatizar o ciclo de produção para validação rápida do sistema.

## Entregas

Criado fluxo:

Pedido → Ordem de Produção → Apontamento → Estoque

### Produção automatizada

Implementado:

- Geração de apontamento de produção via fluxo DEV.
- Consumo automático dos materiais conforme ficha técnica.
- Entrada automática do produto acabado no estoque.

### Endpoint DEV

Criado:

POST `/dev/fluxo/producao/{ordemProducaoId}`

Executa:

- Apontamento da quantidade produzida.
- Baixa de materiais utilizados.
- Entrada do produto finalizado.

## Validação

Fluxo testado com sucesso:

OP:
- OP000010

Movimentações geradas:

- PLA Preto: saída 0.8 KG
- Caixa 3D Porta Figurinhas: entrada 10 unidades

## Resultado

SIGIN possui agora o fluxo básico completo:

Venda → Produção → Estoque

## Sprint 21 - Controle de Status da Ordem de Produção

### Objetivo
Implementar controle de evolução da OP através dos apontamentos de produção.

### Implementado

- Criação de testes de fluxo de produção.
- Validação de geração de OP através de pedido.
- Controle de transição de status:
    - ABERTA
    - EM_PRODUCAO
    - CONCLUIDA

### Regras definidas

- Primeiro apontamento inicia a produção.
- OP não deve ser concluída automaticamente.
- Produção pode ultrapassar a quantidade do pedido.
- Excedente deve permanecer disponível para estoque.
- Encerramento da OP depende de confirmação do usuário.

### Testes criados

- StatusOrdemProducaoTest
- InicioProducaoTest
- ConclusaoOrdemProducaoTest

### Observações futuras

Implementar:
- múltiplos apontamentos por OP;
- produção acumulada;
- encerramento manual da OP;
- controle de excedente para estoque.

## Evolução - Controle de apontamentos múltiplos

Implementado controle acumulado de produção por OP.

Regras:

- Uma OP pode possuir múltiplos apontamentos.
- Cada apontamento soma na quantidade produzida da OP.
- A OP permanece EM_PRODUCAO mesmo atingindo ou ultrapassando a quantidade planejada.
- Encerramento da OP será realizado manualmente pelo usuário.

Teste criado:

- ProducaoAcumuladaTest
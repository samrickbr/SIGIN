# SIGIN — Roadmap do Projeto

## Sistema de Gestão INOVA

O **SIGIN (Sistema de Gestão INOVA)** é um ERP desenvolvido para atender as necessidades da **INOVA Studio 3D**, permitindo controlar desde o cadastro de produtos até todo o fluxo de produção, estoque, custos, vendas e gestão operacional.

O sistema está sendo desenvolvido utilizando arquitetura modular baseada em **Spring Boot**, seguindo princípios de código limpo, separação de responsabilidades, versionamento por sprints e documentação contínua.

---

# Visão Geral do Projeto

## Fase 1 — Fundação

### Sprint 01 — Estrutura Inicial

**Status:** ✅ Concluída

**Entregas**

* Projeto Spring Boot
* PostgreSQL
* Flyway
* Swagger/OpenAPI
* Git/GitHub
* Estrutura base da aplicação

---

### Sprint 02 — Cadastro de Produtos

**Status:** ✅ Concluída

**Entregas**

* Categoria
* Produto
* CRUD completo
* DTOs
* Mappers
* Validações

---

### Sprint 03 — Padronização

**Status:** ✅ Concluída

**Entregas**

* Tratamento de exceções
* Respostas padronizadas
* Organização da arquitetura
* Padronização REST

---

### Sprint 04 — Cadastro de Locais

**Status:** ✅ Concluída

**Entregas**

* Cadastro de Locais
* Exclusão lógica
* StringUtil
* Padronização de dados

---

# Fase 2 — Cadastros Operacionais

### Sprint 05

**Status:** ✅ Concluída

**Entregas**

* Pessoas
* Clientes
* Fornecedores
* Responsáveis
* Materiais
* Impressoras
* Validações
* CRUD completo

---

### Sprint 06

**Status:** ✅ Concluída

**Entregas**

* Estrutura de BOM (Produto x Material)
* Consumo por unidade produzida
* Base para cálculo automático de materiais

---

# Fase 3 — Produção

### Sprint 07

**Status:** ✅ Concluída

**Entregas**

* Ordem de Produção
* Controle de quantidade planejada
* Controle de quantidade produzida
* Origem da produção
* Local de destino

---

### Sprint 08

**Status:** ✅ Concluída

**Entregas**

* Apontamento de Produção
* Controle de perdas
* Atualização automática da OP

---

### Sprint 09

**Status:** ✅ Concluída

**Entregas**

* Movimentação de Estoque
* Entradas
* Saídas
* Produção
* Histórico completo de movimentações

---

### Sprint 10

**Status:** ✅ Concluída

**Entregas**

* Consulta de Saldo
* Estoque calculado por movimentações
* Visão consolidada de materiais e produtos

---

### Sprint 11

**Status:** ✅ Concluída

**Entregas**

* Consumo manual de materiais
* Integração inicial entre produção e estoque

---

### Sprint 12

**Status:** ✅ Concluída

**Entregas**

* Consumo automático baseado na BOM
* Integração entre Produto x Material
* Automatização da baixa de insumos

---

### Sprint 13

**Status:** ✅ Concluída

**Entregas**

* Refatoração do consumo automático
* Centralização da geração de movimentações
* Evolução da arquitetura dos serviços
* Melhor organização das responsabilidades

---

### Sprint 14

**Status:** ✅ Concluída

**Entregas**

* Reserva de Estoque
* Estoque Disponível
* Consulta de materiais reservados
* Reserva automática na criação da Ordem de Produção
* Validação de disponibilidade
* Rollback transacional

---

# Fase 4 — Produção Inteligente

### Sprint 15

**Status:** 🚧 Em desenvolvimento

**Objetivos**

* Máquina de estados da Ordem de Produção
* Fluxo operacional da produção
* Início da produção
* Conclusão da produção
* Cancelamento
* Falha de produção
* Reabertura de OP
* Baixa automática das reservas
* Sprint 15
* Máquina de estados da OP
* Reserva de estoque 
* Integração Postman Collection 
* Documentação automática da API

---

### Sprint 16

Planejamento da produção.

* Filas
* Prioridades
* Planejamento
* Capacidade produtiva

---

### Sprint 17

Integração com Impressoras.

* Associação da OP
* Tempo previsto
* Tempo realizado
* Histórico por equipamento

---

# Fase 5 — Comercial

### Sprint 18

* Pedidos
* Clientes
* Itens
* Integração Pedido → OP

---

### Sprint 19

* Expedição
* Separação
* Entrega
* Histórico de vendas

---

# Fase 6 — Gestão

### Sprint 20

Documentação completa do SIGIN.

Será gerado o **SIGIN Development Guide**, contendo:

* Arquitetura
* Banco de Dados
* Diagramas
* Fluxos
* Convenções
* Roadmap
* Manual técnico

---

### Sprint 21

Dashboards.

* Produção
* Estoque
* Custos
* Eficiência
* Indicadores

---

### Sprint 22

Financeiro.

* Custos
* Receitas
* Fluxo de caixa
* Rentabilidade

---

### Sprint 23

Usuários e Permissões.

* Login
* Perfis
* Papéis
* Auditoria

---

### Sprint 24

Multiempresa.

* Empresas
* Filiais
* Estoques independentes
* Produção independente

---

### Sprint 25

API Pública.

* Tokens
* Integrações
* Marketplace
* ERP externo

---

# Princípios do Projeto

## Estoque

O estoque nunca será alterado diretamente.

Toda alteração deverá gerar uma movimentação.

Fluxo:

Pedido

↓

Ordem de Produção

↓

Reserva

↓

Movimentação

↓

Estoque

---

## Produção

A Ordem de Produção representa o centro operacional do sistema.

Todo o fluxo produtivo será controlado através de estados e eventos.

---

## Arquitetura

O SIGIN utiliza arquitetura em camadas:

* Controller
* DTO
* Mapper
* Service
* Repository
* Entity

Toda regra de negócio permanece na camada de Service.

---

## Evolução

Cada Sprint somente será considerada concluída após:

1. Desenvolvimento
2. Testes
3. Documentação
4. Versionamento
5. Commit
6. Push

---

## Visão de Longo Prazo

O objetivo do SIGIN é evoluir de um sistema de gestão para um ERP completo especializado em manufatura aditiva (Impressão 3D), permitindo controlar integralmente os processos da INOVA Studio 3D e servindo como base para futuras integrações com impressoras, marketplaces e sistemas externos.

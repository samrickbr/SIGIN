# SIGIN — Roadmap do Projeto

## Sistema de Gestão INOVA

O SIGIN (Sistema de Gestão INOVA) tem como objetivo controlar os processos internos da INOVA Studio 3D, abrangendo produção, estoque, vendas, custos e gestão operacional.

A arquitetura será desenvolvida de forma modular, permitindo evolução gradual conforme a necessidade do negócio.

---

# Fase 1 — Fundação do Sistema

## Sprint 01 — Estrutura inicial

**Status:** ✅ Concluída

### Objetivos

Criar a base técnica do projeto.

### Entregas

* Projeto Spring Boot
* Configuração PostgreSQL
* Flyway para migrations
* Swagger/OpenAPI
* Configuração Git/GitHub
* Estrutura inicial de pacotes

---

## Sprint 02 — Cadastro de Produtos

**Status:** ✅ Concluída

### Objetivos

Criar a base de cadastro dos itens comercializados pela INOVA.

### Entregas

* Cadastro de Produto
* Cadastro de Categoria
* Relacionamento Produto x Categoria
* CRUD completo
* Validações iniciais
* DTOs

---

## Sprint 03 — Padronização e tratamento

**Status:** ✅ Concluída

### Objetivos

Melhorar a estrutura interna da aplicação.

### Entregas

* Padronização de respostas
* Tratamento inicial de exceções
* Organização dos pacotes
* Melhorias na arquitetura REST

---

## Sprint 04 — Cadastro de Locais

**Status:** ✅ Concluída

### Objetivos

Criar a estrutura para controle dos locais físicos e lógicos utilizados pelo estoque.

### Entregas

* Entidade Local
* Migration V5
* CRUD completo
* Exclusão lógica
* Padronização de nomes
* Utilitário StringUtil
* Regras iniciais de qualidade dos dados

---

# Fase 2 — Cadastros Operacionais

## Sprint 05 — Cadastros auxiliares

**Status:** ⏳ Planejada

### Objetivo

Criar os cadastros necessários para suportar a operação da INOVA.

---

## Cliente

Responsável por armazenar informações dos compradores e demandas externas.

Campos iniciais:

* Nome
* Telefone
* Email
* Observações
* Status ativo/inativo

---

## Fornecedor

Responsável por armazenar parceiros e fornecedores.

Campos iniciais:

* Nome
* Contato
* Telefone
* Email
* Observações
* Status ativo/inativo

---

## Impressora

Responsável pelo controle dos equipamentos utilizados na produção.

Campos iniciais:

* Nome
* Modelo
* Identificação
* Status
* Observações

Exemplos:

* Ender 3 V2 Neo
* Bambu Lab A1

---

## Material

Responsável pelo controle de insumos utilizados.

Campos iniciais:

* Nome
* Tipo
* Fabricante
* Cor
* Peso comprado
* Estoque disponível

Exemplos:

* PLA Preto Elegoo
* PETG Branco
* TPU Azul

---

# Fase 3 — Comercial

## Sprint 06 — Pedidos e Encomendas

**Status:** ⏳ Planejada

### Objetivo

Controlar demandas comerciais e necessidades de produção.

Tipos de demanda:

* Venda para cliente
* Produção para estoque
* Reposição
* Mostruário
* Parceiros

Entidades previstas:

* Pedido
* PedidoItem

---

# Fase 4 — Produção

## Sprint 07 — Ordem de Produção

**Status:** ⏳ Planejada

### Objetivo

Controlar o processo produtivo.

Informações previstas:

* Produto
* Quantidade planejada
* Quantidade produzida
* Perdas
* Impressora utilizada
* Material utilizado
* Prazo
* Origem da demanda

---

# Fase 5 — Estoque

## Sprint 08 — Movimentações

**Status:** ⏳ Planejada

### Objetivo

Criar o histórico completo das alterações de estoque.

O estoque será calculado através das movimentações registradas.

Tipos previstos:

* ENTRADA_COMPRA
* PRODUCAO
* SAIDA_VENDA
* TRANSFERENCIA
* PERDA_PRODUCAO
* PERDA_ESTOQUE
* CONSUMO_OPERACIONAL
* DEVOLUCAO
* AJUSTE_INVENTARIO

---

# Fase 6 — Gestão e Inteligência

## Sprint 09 — Custos

**Status:** ⏳ Planejada

Objetivos:

* Controle de custo de material
* Custo de produção
* Tempo de máquina
* Margem de lucro

---

## Sprint 10 — Relatórios

**Status:** ⏳ Planejada

Relatórios previstos:

* Estoque atual
* Produção
* Perdas
* Vendas
* Rentabilidade
* Indicadores operacionais

---

# Princípios do projeto

## Estoque

O estoque nunca será alterado diretamente.

Toda alteração deverá gerar uma movimentação.

Modelo:

```
Pedido
   ↓
Ordem de Produção
   ↓
Movimentação
   ↓
Estoque
```

---

## Dados

Todos os cadastros seguirão regras padronizadas:

* Nomes normalizados
* Códigos técnicos sem acentos
* Histórico preservado
* Exclusão lógica quando aplicável

---

## Evolução

O SIGIN será desenvolvido por etapas, com cada sprint contendo:

1. Planejamento
2. Desenvolvimento
3. Testes
4. Documentação
5. Commit e versionamento

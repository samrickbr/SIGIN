# Modelo do Banco de Dados

## Produtos

Campos principais:
- id
- codigo
- nome
- descricao
- categoria_id
- ativo
- data_criacao

Relacionamento:
- Produto pertence a Categoria


## Categorias

Campos principais:
- id
- nome
- ativo
- data_criacao


## Locais

Campos principais:
- id
- nome
- descricao
- ativo
- data_criacao


## Pessoas

Campos principais:
- id
- nome
- tipo_documento
- documento
- telefone
- email
- observacao
- ativo
- data_criacao


## Tipos de Pessoa

Campos principais:
- id
- nome
- descricao
- ativo
- data_criacao


## Pessoa Tipos

Tabela de relacionamento:

- pessoa_id
- tipo_pessoa_id
- data_criacao

Relacionamentos:
- Pessoa pode possuir vários tipos
- TipoPessoa pode pertencer a várias pessoas


## Materiais

Campos principais:
- id
- codigo
- nome
- descricao
- unidade_medida
- estoque_minimo
- ativo
- data_criacao

## Produto x Material

Tabela:

* produto_material

Campos principais:

* id
* produto_id
* material_id
* quantidade
* ativo
* data_criacao

Regra:
A quantidade representa o consumo do material para produzir uma unidade do produto.

Exemplo:
Produto: Chaveiro Taça Copa 2026 Premium

Material:
PLA Preto

Quantidade:
0,018 kg por unidade produzida.

## Ordens de Produção

Campos principais:
- id
- numero
- produto_id
- quantidade_planejada
- quantidade_produzida
- local_destino_id
- responsavel_id
- status
  - Tipo:
  - Enum(StatusOrdemProducao)
    - Valores:
      - ABERTA
      - RESERVADA
      - EM_PRODUCAO
      - CONCLUIDA
      - CANCELADA
      - FALHA_PRODUCAO
      - origem
      - observacao
      - ativo
      - data_abertura
      - data_inicio
      - data_fim

Relacionamentos:
- Ordem de Produção pertence a um Produto
- Ordem de Produção possui um Local destino
- Ordem de Produção pode possuir um responsável

## OP Materiais

Campos principais:
- id
- ordem_producao_id
- material_id
- quantidade_planejada
- quantidade_consumida
- ativo
- data_criacao

Relacionamentos:
- Pertence a uma Ordem de Produção
- Utiliza um Material

## Apontamentos de Produção

Campos principais:
- id
- ordem_producao_id
- quantidade_produzida
- quantidade_perda
- observacao
- responsavel_id
- data_apontamento
- ativo

Relacionamentos:
- Pertence a uma Ordem de Produção
- Possui um responsável

## Movimentações de Estoque

Campos principais:
- id
- produto_id
- material_id
- local_id
- tipo
- quantidade
- origem
- referencia_id
- observacao
- responsavel_id
- data_movimentacao
- ativo

Regras:
- Uma movimentação deve possuir produto ou material.
- Não é permitido informar os dois.
- Toda movimentação pertence a um local.

Relacionamentos:
- Pode estar vinculada a um Produto
- Pode estar vinculada a um Material
- Possui um Local
- Pode possuir um responsável

### Campo movimento

Valores possíveis:

* ENTRADA
* SAIDA

Utilização:

ENTRADA

* Compra
* Produção
* Ajuste positivo

SAIDA

* Consumo de produção
* Venda
* Perda
* Ajuste negativo

O saldo do estoque é calculado dinamicamente:

Saldo = Soma(ENTRADAS) - Soma(SAIDAS)

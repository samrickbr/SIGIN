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
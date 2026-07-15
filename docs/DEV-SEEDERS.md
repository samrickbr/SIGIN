# Dev Seeders

## Endpoint

POST /dev/popular


## Objetivo

Popular o banco de desenvolvimento com dados mínimos para testes.


## Ordem de execução

1. TipoPessoaSeeder
2. PessoaSeeder
3. PessoaTipoSeeder
4. MaterialSeeder
5. ProdutoSeeder
6. ProdutoMaterialSeeder


## Regras

- Seeders não duplicam registros existentes.
- Todos usam validação antes de inserir.
- Exclusivo para ambiente de desenvolvimento.


## Dados criados

### Pessoas
- Cliente Teste

### Tipos
- CLIENTE
- FORNECEDOR
- FUNCIONARIO

### Materiais
- PLA Preto
- PLA Branco

### Produtos
- Caixa 3D Porta Figurinhas
- Chaveiro Taça Copa 2026 Premium

### Ficha técnica
- Produto → Material
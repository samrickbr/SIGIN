# DEV-SEEDERS

## Objetivo

Documentar os dados iniciais utilizados durante o desenvolvimento do SIGIN.

Os Seeders possuem como objetivo facilitar testes locais, validações de regras de negócio e desenvolvimento dos módulos.

---

# Ordem de execução

A ordem dos Seeders deve respeitar as dependências entre entidades.

```text id="7j8h9k"
1. Configurações
        ↓
2. Pessoas
        ↓
3. Produtos
        ↓
4. Canais de Venda
        ↓
5. Produto x Canal
        ↓
6. Produto Venda
        ↓
7. Pedidos/Testes
```

---

# CanalVenda Seeder

## Objetivo

Criar canais comerciais disponíveis no sistema.

Exemplos:

```text id="u2l3m4"
Loja Física
Delivery
WhatsApp
Marketplace
```

Estrutura:

```text
CanalVenda
    id
    nome
    descricao
    ativo
```

---

# ProdutoCanal Seeder

## Objetivo

Criar permissões de venda dos produtos por canal.

Responsabilidade:

* definir onde o produto pode ser vendido;
* controlar disponibilidade do produto naquele canal.

Exemplo:

```text id="n7p8q9"
Produto:
  Pizza Calabresa

Canais:
  ✓ Loja Física
  ✓ Delivery
  ✗ Marketplace
```

Estrutura:

```text
ProdutoCanal

produto_id
canal_venda_id
ativo
```

---

# ProdutoVenda Seeder

## Objetivo

Criar informações comerciais dos produtos.

Responsabilidade:

* preço;
* imagem;
* disponibilidade comercial.

Exemplo:

```text id="r4s5t6"
Produto:
  Pizza Calabresa

Canal:
  Delivery

Preço:
  R$ 29,90
```

Estrutura:

```text
ProdutoVenda

produto_id
canal_venda_id
preco_venda
imagem
disponivel_venda
```

---

# Dependências

Antes de criar um `ProdutoVenda` deve existir:

```text id="v5w6x7"
Produto
   +
CanalVenda
   +
ProdutoCanal
```

Fluxo:

```text id="a8b9c0"
Produto
   |
   ▼
CanalVenda
   |
   ▼
ProdutoCanal
   |
   ▼
ProdutoVenda
```

---

# Dados mínimos para testes comerciais

Para testar criação de pedidos:

Necessário possuir:

* Cliente cadastrado;
* Produto cadastrado;
* CanalVenda cadastrado;
* ProdutoCanal ativo;
* ProdutoVenda com preço definido.

---

# Exemplo de cenário de teste

Produto:

```text
Produto ID: 1
Nome: Produto Teste
```

Canal:

```text
Canal ID: 2
Nome: Delivery
```

Disponibilidade:

```text
ProdutoCanal

Produto: 1
Canal: 2
Ativo: true
```

Preço:

```text
ProdutoVenda

Produto: 1
Canal: 2
Preço: 29,90
Disponível: true
```

Pedido:

```json
{
  "clienteId": 1,
  "canalVendaId": 2,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    }
  ]
}
```

Resultado esperado:

```text
Valor unitário:
29,90

Valor total:
59,80
```

---

# Observações

A partir da Sprint 04:

* nenhum canal deve cadastrar preços próprios;
* nenhum módulo externo deve enviar valor unitário;
* todas as regras comerciais devem passar pelo Core.

O Seeder deve sempre respeitar as mesmas regras utilizadas em produção.

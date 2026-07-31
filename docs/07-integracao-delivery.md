# Integração Delivery

## Objetivo

O módulo Delivery não possui regras comerciais próprias.

Toda a lógica de disponibilidade de produtos, preços e validações é responsabilidade do Core do SIGIN.

O Delivery atua apenas como consumidor das APIs disponibilizadas pelo Core.

---

# Fluxo

```text
Cliente
    │
    ▼
Delivery
    │
    ▼
Core SIGIN
    │
    ├── ProdutoCanalService
    │
    ├── ProdutoVendaService
    │
    ├── PedidoService
    │
    └── Estoque
```

---

# Cardápio

O cardápio deve listar apenas produtos disponíveis para o canal solicitado.

Fluxo:

```text
Canal de Venda
        │
        ▼
ProdutoCanal
        │
        ▼
ProdutoVenda
        │
        ▼
Cardápio
```

Regras:

* Produto precisa estar habilitado para o canal.
* Produto precisa possuir cadastro comercial.
* Produto precisa estar disponível para venda.
* O preço sempre é obtido do Core.

---

# Criação de Pedido

O Delivery nunca informa preços.

O frontend envia apenas:

```json
{
  "clienteId": 1,
  "canalVendaId": 2,
  "itens": [
    {
      "produtoId": 5,
      "quantidade": 2
    }
  ]
}
```

---

# Fluxo interno

```text
Pedido
    │
    ▼
ProdutoCanalService
    │
    ▼
ProdutoVendaService
    │
    ▼
PedidoItem
    │
    ▼
Pedido
```

O Core executa automaticamente:

* validação do canal;
* validação de disponibilidade;
* obtenção do preço;
* cálculo do valor do item;
* cálculo do valor total do pedido.

---

# Responsabilidades

## Delivery

Responsável por:

* autenticação do cliente;
* carrinho;
* interface;
* acompanhamento do pedido.

Não é responsável por:

* cálculo de preços;
* disponibilidade;
* regras comerciais;
* estoque;
* produção.

---

## Core

Responsável por:

* cadastro de produtos;
* canais de venda;
* disponibilidade por canal;
* preços por canal;
* criação de pedidos;
* estoque;
* produção;
* regras comerciais.

---

# Benefícios

* Um único ponto de manutenção das regras comerciais.
* Eliminação de duplicidade de lógica entre módulos.
* Facilidade para integrar novos canais.
* Garantia de consistência entre Delivery, PDV, Marketplace e futuras integrações.

---

# Canais suportados

A arquitetura foi preparada para suportar múltiplos canais de venda.

Exemplos:

* Loja Física
* Delivery
* WhatsApp
* Marketplace
* E-commerce
* API Pública
* Aplicativo Mobile

Todos utilizam exatamente as mesmas regras comerciais disponibilizadas pelo Core.

---

# Situação atual

A partir da Sprint 04, o Delivery passa a consumir integralmente as regras comerciais do Core, deixando de possuir qualquer responsabilidade sobre preços ou disponibilidade de produtos.

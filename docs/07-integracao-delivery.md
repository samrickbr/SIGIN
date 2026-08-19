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

---

# Sprint P0 — Integração Delivery × Core

## Objetivo

Integrar o Delivery existente ao SIGIN Core preservando seu fluxo operacional.

O Core permanece como autoridade dos conceitos comerciais compartilhados.

Arquitetura:

```
SIGIN Core
    ↓
Delivery Back
    ↓
Delivery Front

```
O SIGIN Admin continua administrando os dados do Core.

Responsabilidades
SIGIN Core

Responsável por:

Produto;
Categoria;
Canal de Venda;
ProdutoCanal;
ProdutoVenda;
preço;
disponibilidade comercial;
Pessoa/cliente;
forma de pagamento;
Pedido;
PedidoItem;
produção;
estoque;
regras comerciais.
Delivery Back

Responsável por:

adaptar os contratos do Core;
orquestrar o fluxo do canal Delivery;
manter a operação específica do Delivery;
receber e validar dados do Front;
resolver cliente no Core;
criar pedido no Core;
manter vínculo entre pedido Delivery e pedido Core;
preservar o fluxo operacional existente.
Delivery Front

Responsável por:

catálogo;
carrinho;
checkout;
identificação do cliente;
endereço;
seleção da forma de pagamento;
acompanhamento do pedido.

O Front nunca é autoridade de preço, disponibilidade ou total comercial.

Catálogo

O catálogo comercial deve ser obtido do Core respeitando:

Produto
↓
ProdutoCanal
↓
ProdutoVenda
↓
CanalVenda

O Delivery não mantém catálogo comercial paralelo.

Preço e disponibilidade devem ser obtidos do Core.

Checkout

O Front pode apresentar valores calculados para fins de experiência.

Na finalização, o Delivery Back deve utilizar os dados oficiais do Core.

Não são confiáveis:

preço enviado pelo navegador;
subtotal enviado pelo navegador;
total enviado pelo navegador.

O Core deve validar e determinar os valores comerciais oficiais.

Cliente

O Core é autoridade da identidade do cliente.

Fluxo:

Cliente existente
↓
Localizar no Core
↓
Reutilizar


Cliente inexistente
↓
Cadastrar no Core
↓
Utilizar identificação retornada

A integração deve evitar duplicação de pessoas.

Pedido

O pedido comercial deve ser criado no Core.

O Delivery pode manter uma representação operacional própria quando necessária para preservar o fluxo existente.

A relação deve ser persistente e rastreável:

Pedido Delivery
↓
Pedido Core

O Delivery não deve criar uma segunda autoridade comercial para pedido, preço ou total.

Estados

Os estados comerciais do Core e os estados operacionais do Delivery não precisam ser idênticos.

O Delivery preserva seus estados específicos de operação, incluindo:

recebimento;
aprovação;
produção;
separação;
saída para entrega;
entrega.

A sincronização entre Core e Delivery deve ocorrer somente quando necessária.

Estoque

O estoque permanece sob autoridade do Core.

Esta Sprint não cria estoque paralelo no Delivery nem define uma regra exclusiva de movimentação de estoque para Delivery.

Decisões globais sobre movimentação de estoque permanecem no domínio do Core.

Ambiente

O SIGIN Core possui configuração separada por ambiente:

application.yml
application-dev.yml
application-prod.yml
Desenvolvimento

O profile dev utiliza o PostgreSQL local.

Produção

O profile prod utiliza variáveis de ambiente para conexão com o PostgreSQL.

Credenciais de produção não devem ser armazenadas no repositório.

Objetivo operacional

A integração P0 busca permitir o fluxo:

Admin
↓
Configuração comercial no Core
↓
Catálogo Delivery
↓
Carrinho
↓
Cliente
↓
Checkout
↓
Pedido Core
↓
Pedido Delivery
↓
Balcão
↓
Aprovação
↓
Produção
↓
Separação
↓
Entrega
↓
Conclusão

A prioridade é tornar o Delivery funcional utilizando o Core como autoridade, sem reconstruir o Delivery e sem duplicar regras comerciais.


## Cliente Delivery — Identidade Autenticável

O cliente do Delivery utiliza a identidade oficial do SIGIN Core.

Fluxo:

Pessoa
↓
Usuario
↓
Perfil CLIENTE
↓
JWT

O Delivery não possui tabela própria de Pessoa, Usuario ou senha.

### Cadastro

O cadastro público do cliente utiliza:

- nome completo;
- CPF;
- telefone/WhatsApp;
- email, quando informado;
- senha.

O Core materializa:

1. Pessoa;
2. Usuario vinculado à Pessoa;
3. Usuario.login = CPF;
4. senha armazenada utilizando o PasswordEncoder existente;
5. Perfil CLIENTE associado ao Usuario.

CPF é a identidade de login do cliente.

### Autenticação

O cliente utiliza o mecanismo existente:

CPF + senha
↓
POST /auth/login
↓
JWT

Não existe autenticação paralela para Delivery.

### Perfil CLIENTE

Foi criado o perfil CLIENTE por migration:

V44__criar_perfil_cliente.sql

O perfil não recebe permissões administrativas.

### Implementação

O fluxo de cadastro foi evoluído em:

ClienteDeliveryService

O DTO de cadastro passou a receber a senha em:

ClienteRequest

A senha permanece exclusivamente no Usuario e é processada pelo mecanismo de senha existente no Core.

### Regra de identidade

Um CPF representa uma identidade de cliente.

Quando a Pessoa já existe, o fluxo verifica a existência de Usuario pelo pessoaId antes de criar uma nova identidade.

O Delivery permanece consumidor da identidade oficial do Core.

---

### Identificação do cliente

O Delivery utiliza os seguintes endpoints públicos para identificação do cliente:

- `POST /api/delivery/clientes` — cadastro de cliente
- `POST /auth/login` — autenticação do cliente existente

O cadastro do cliente é público porque ocorre antes da autenticação.

Após o cadastro, o cliente pode utilizar CPF e senha para autenticação.
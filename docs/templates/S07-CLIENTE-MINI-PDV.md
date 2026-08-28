# Sprint 07 — Pesquisa e Cadastro de Cliente para Mini PDV

## Objetivo

Preparar o SIGIN Core para fornecer contratos operacionais para pesquisa, seleção, cadastro e endereçamento de clientes utilizados futuramente pelo Mini PDV.

O SIGIN Core permanece como autoridade sobre Pessoa, Cliente, documento, telefone, e-mail e endereço.

O `delivery-front` e o `delivery-back` permanecem consumidores desses contratos.

---

## Escopo realizado

### Pesquisa unificada

Disponibilizado:

```http
GET /api/delivery/clientes?busca={termo}
```

A pesquisa aceita um único termo e permite localizar clientes por:

* nome;
* documento;
* telefone.

A pesquisa retorna somente pessoas classificadas como `CLIENTE` e ativas.

Resposta enxuta:

```json
[
  {
    "id": 22,
    "nome": "Cliente Operacional",
    "telefone": "123654",
    "documento": "6547896321",
    "email": "1@1.com"
  }
]
```

Quando nenhum cliente é encontrado:

```json
[]
```

O identificador retornado é o `Pessoa.id` oficial.

A pesquisa não cria nem altera dados.

---

## Cadastro operacional

Disponibilizado:

```http
POST /api/delivery/clientes/operacional
```

O cadastro operacional permite:

```json
{
  "nome": "Cliente Operacional",
  "documento": "6547896321",
  "telefone": "123654",
  "email": "1@1.com"
}
```

O fluxo operacional:

```text
Operador
   ↓
Cadastro operacional
   ↓
Pessoa
   ↓
Tipo CLIENTE
   ↓
Pessoa.id
```

O cadastro operacional não exige senha e não cria `Usuario`.

O fluxo existente de autocadastro permanece separado e continua utilizando seu contrato atual com senha.

---

## Endereço operacional

Disponibilizado:

```http
POST /api/delivery/clientes/{clienteId}/enderecos
```

O endereço utiliza exclusivamente a estrutura existente:

```text
PessoaEndereco
```

Não foi criada uma nova entidade de endereço.

Exemplo validado:

```text
Cliente:
Pessoa.id = 22

Endereço:
PessoaEndereco.id = 20
principal = true
```

---

## Segurança

Os recursos operacionais foram protegidos por autenticação:

```text
GET  /api/delivery/clientes
POST /api/delivery/clientes/operacional
POST /api/delivery/clientes/{clienteId}/enderecos
```

O autocadastro existente permanece público:

```text
POST /api/delivery/clientes
```

Não foi criada autenticação paralela.

---

## Compatibilidade

Foram preservados:

```http
GET /api/delivery/clientes/telefone/{telefone}

GET /api/delivery/clientes/documento/{documento}

POST /api/delivery/clientes
```

Também foram preservados os fluxos existentes de:

* autocadastro;
* login do cliente;
* consulta de endereços;
* criação de endereços.

Nenhuma regra de pedido, pagamento, caixa ou fechamento de venda foi alterada.

---

## Integridade e duplicidade

O cadastro operacional valida duplicidade de:

```text
documento
telefone
```

Documento já existente:

```text
"Já existe uma pessoa cadastrada com este documento."
```

Telefone já existente:

```text
"Já existe uma pessoa cadastrada com este telefone."
```

Os testes funcionais confirmaram que clientes duplicados não são criados.

---

## Validação funcional

### Pesquisa por nome

```text
GET /api/delivery/clientes?busca=Cliente
```

Resultado: aprovado.

### Pesquisa por documento

```text
GET /api/delivery/clientes?busca=6547896321
```

Resultado: aprovado.

### Pesquisa por telefone

```text
GET /api/delivery/clientes?busca=123654
```

Resultado: aprovado.

### Cliente inexistente

Resultado: lista vazia.

### Cadastro operacional

Resultado: aprovado.

Cliente criado:

```text
Pessoa.id = 22
```

### Cadastro de endereço

Resultado: aprovado.

Endereço criado:

```text
PessoaEndereco.id = 20
```

### Documento duplicado

Resultado: rejeitado.

### Telefone duplicado

Resultado: rejeitado.

### Segurança

Resultados validados:

```text
não autenticado → 401
autenticado → permitido
```

### Testes automatizados

Não foram adicionados novos testes automatizados nesta Sprint, pois a suíte existente encontra-se defasada e não representa adequadamente o estado atual do Core.

A validação desta Sprint foi realizada por:

* auditoria do código;
* Swagger;
* testes funcionais dos contratos;
* validação de segurança;
* validação de duplicidade;
* validação de regressão dos endpoints existentes.

---

## Contrato final

### Pesquisa

```http
GET /api/delivery/clientes?busca={termo}
Authorization: Bearer {token}
```

### Cadastro operacional

```http
POST /api/delivery/clientes/operacional
Authorization: Bearer {token}
Content-Type: application/json
```

Request:

```json
{
  "nome": "Cliente",
  "documento": "12345678900",
  "telefone": "42999999999",
  "email": "cliente@email.com"
}
```

### Endereço

```http
POST /api/delivery/clientes/{clienteId}/enderecos
Authorization: Bearer {token}
Content-Type: application/json
```

---

## Identificador oficial

O Mini PDV deverá utilizar:

```text
Pessoa.id
```

como identificador técnico do cliente.

Não deve criar identificador local, UUID paralelo ou utilizar documento/telefone como identificador técnico.

---

## Fluxo resultante

```text
                 MINI PDV
                    │
                    ▼
             PESQUISAR CLIENTE
                    │
          ┌─────────┴─────────┐
          ▼                   ▼
    CLIENTE EXISTE       NÃO ENCONTRADO
          │                   │
          ▼                   ▼
      SELECIONAR          NOVO CLIENTE
          │                   │
          │              DADOS CADASTRAIS
          │                   │
          │                   ▼
          │             CLIENTE CRIADO
          │                   │
          │                   ▼
          │              ENDEREÇO
          │                   │
          └──────────┬────────┘
                     ▼
             CLIENTE DISPONÍVEL
                PARA VENDA
```

---

## Fora do escopo

Não foram implementados:

* Mini PDV;
* venda;
* pagamento;
* caixa;
* fechamento;
* comanda;
* estoque;
* alterações no Delivery Back;
* alterações no Delivery Front;
* alterações no SIGIN Admin;
* banco local de clientes;
* entidade Cliente duplicada;
* entidade Pessoa duplicada;
* entidade PessoaEndereco duplicada;
* autenticação paralela.

---

## Resultado

Sprint concluída.

O SIGIN Core passa a fornecer contratos estáveis para:

```text
PESQUISAR
CADASTRAR
ENDEREÇAR
SELECIONAR
```

mantendo o Core como única autoridade dos dados de cliente.

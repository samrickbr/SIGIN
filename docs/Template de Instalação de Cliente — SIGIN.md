# Template de Instalação de Cliente — SIGIN

## 1. Objetivo

Este documento define o padrão para provisionar uma instalação completa do SIGIN para um cliente.

Cada cliente possui uma instalação independente, incluindo:

* SIGIN Core;
* SIGIN Admin;
* módulos específicos;
* bancos de dados próprios;
* variáveis de ambiente próprias;
* domínios próprios;
* ambientes de testes e produção separados.

O objetivo é transformar uma nova instalação em um processo previsível e repetível.

---

# 2. Princípio de isolamento

Cada cliente deve possuir infraestrutura isolada.

```text
CLIENTE
│
├── TESTES
│   ├── Core
│   ├── Admin
│   ├── Módulos
│   └── Banco
│
└── PRODUÇÃO
    ├── Core
    ├── Admin
    ├── Módulos
    └── Banco
```

Não compartilhar entre clientes:

* banco de dados;
* credenciais;
* JWT secrets;
* variáveis de ambiente;
* dados;
* domínios;
* configurações específicas do cliente.

O código-fonte, entretanto, permanece centralizado nos repositórios oficiais.

---

# 3. Repositórios oficiais

Os serviços são obtidos diretamente dos repositórios oficiais.

## Core

`https://github.com/samrickbr/SIGIN`

## Admin

`https://github.com/samrickbr/sigin-admin`

## Delivery Back

`https://github.com/samrickbr/delivery-back`

## Delivery Front

`https://github.com/samrickbr/delivery-front`

Novos módulos deverão seguir o mesmo princípio.

---

# 4. Estrutura no Coolify

Cada cliente deverá possuir uma estrutura própria.

Exemplo:

```text
COOLIFY
│
├── CLIENTE-A
│   ├── TESTES
│   │   ├── PostgreSQL
│   │   ├── SIGIN Core
│   │   ├── SIGIN Admin
│   │   ├── Delivery Back
│   │   └── Delivery Front
│   │
│   └── PRODUÇÃO
│       ├── PostgreSQL
│       ├── SIGIN Core
│       ├── SIGIN Admin
│       ├── Delivery Back
│       └── Delivery Front
│
└── CLIENTE-B
    └── ...
```

A nomenclatura definitiva pode ser adaptada ao padrão de organização utilizado no Coolify.

---

# 5. Ambientes

Cada cliente terá pelo menos dois ambientes:

```text
TESTES
PRODUÇÃO
```

Os ambientes devem ser independentes.

## TESTES

Utilizado para:

* validação;
* homologação;
* testes de integração;
* validação de novas versões;
* testes de configuração.

## PRODUÇÃO

Utilizado pelos usuários reais.

Nenhuma configuração de testes deve apontar para recursos de produção.

---

# 6. Banco de dados

Cada ambiente possui banco próprio.

```text
Cliente A
│
├── PostgreSQL TESTES
└── PostgreSQL PRODUÇÃO
```

Outro cliente:

```text
Cliente B
│
├── PostgreSQL TESTES
└── PostgreSQL PRODUÇÃO
```

Nunca utilizar o mesmo banco para dois clientes.

Nunca utilizar o banco de produção durante testes.

---

# 7. SIGIN Core

O Core é a base da instalação.

Runtime:

```text
Java 21
Spring Boot
Maven
PostgreSQL 17
Flyway
```

O Core deve ser configurado com:

* banco do próprio ambiente;
* credenciais próprias;
* JWT/configurações de autenticação próprias;
* domínio próprio;
* variáveis específicas do cliente.

Exemplo conceitual:

```text
CLIENTE-A
TESTES
    Core → PostgreSQL TESTES A

CLIENTE-A
PRODUÇÃO
    Core → PostgreSQL PRODUÇÃO A
```

---

# 8. SIGIN Admin

O Admin é o frontend administrativo.

Runtime:

```text
Node.js 24.x
npm 11.x
React
Vite
TypeScript
```

O `package.json` deve declarar o runtime quando necessário.

Padrão atual:

```json
"engines": {
  "node": "24.x",
  "npm": "11.x"
}
```

Build:

```bash
npm install
npm run build
```

O ambiente do Coolify deve respeitar a versão declarada pelo projeto.

---

# 9. Delivery Back

O Delivery Back deve utilizar:

```text
Java 21
Spring Boot
Maven
PostgreSQL
RestClient
```

O serviço deve apontar para:

* Core do mesmo cliente;
* banco do próprio ambiente;
* variáveis do próprio ambiente.

Exemplo:

```text
Delivery Back TESTES
        ↓
Core TESTES
        ↓
PostgreSQL TESTES
```

Nunca:

```text
Delivery Back TESTES
        ↓
Core PRODUÇÃO
```

---

# 10. Delivery Front

O Delivery Front deve apontar exclusivamente para o Delivery Back correspondente ao mesmo cliente e ambiente.

Exemplo:

```text
Delivery Front TESTES
        ↓
Delivery Back TESTES
        ↓
Core TESTES
        ↓
PostgreSQL TESTES
```

Produção segue a mesma cadeia utilizando somente recursos de produção.

---

# 11. Cadeia de dependências

A instalação deve respeitar a seguinte ordem:

```text
PostgreSQL
    ↓
Core
    ↓
Admin
    ↓
Delivery Back
    ↓
Delivery Front
```

O Core precisa estar funcional antes dos serviços que dependem dele.

O Delivery Back precisa estar funcional antes do Delivery Front.

---

# 12. Variáveis de ambiente

Cada aplicação deve possuir variáveis próprias.

Exemplos conceituais:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
CORE_URL
API_URL
JWT_SECRET
```

Os nomes reais devem ser definidos pelo projeto.

Valores nunca devem ser copiados automaticamente entre clientes.

---

# 13. Domínios

Cada cliente possui seus próprios domínios.

Exemplo:

```text
Cliente A
├── admin.cliente-a.com.br
├── api.cliente-a.com.br
└── delivery.cliente-a.com.br
```

Testes:

```text
Cliente A
├── admin-test.cliente-a.com.br
├── api-test.cliente-a.com.br
└── delivery-test.cliente-a.com.br
```

Os nomes podem variar conforme a estratégia comercial.

O código não deve depender de domínio fixo.

---

# 14. ROTA

O ROTA possui infraestrutura própria.

A instalação pode ser preparada antes da definição do domínio definitivo.

Estrutura:

```text
ROTA
├── Rota Back
├── Rota Front
└── PostgreSQL, quando necessário
```

O domínio poderá ser configurado posteriormente.

A alteração do domínio deve ser uma configuração de infraestrutura, não uma alteração de código.

---

# 15. Runtime obrigatório

Referência atual:

| Serviço        | Runtime                    |
| -------------- | -------------------------- |
| SIGIN Core     | Java 21                    |
| SIGIN Admin    | Node 24 / npm 11           |
| Delivery Back  | Java 21                    |
| Delivery Front | Node definido pelo projeto |
| PostgreSQL     | 17                         |

Antes de criar uma aplicação no Coolify, verificar a versão efetiva exigida pelo repositório.

---

# 16. Processo de instalação

## Etapa 1 — Criar ambiente

Criar o ambiente do cliente no Coolify.

Definir:

* nome;
* servidor;
* ambiente;
* identificação do cliente.

## Etapa 2 — Criar banco

Criar PostgreSQL exclusivo.

Registrar:

* host;
* porta;
* database;
* usuário;
* credencial.

## Etapa 3 — Criar Core

Configurar:

* repositório;
* branch;
* runtime;
* variáveis;
* banco;
* domínio;
* healthcheck.

Executar deploy.

## Etapa 4 — Validar Core

Verificar:

* container;
* logs;
* migrations;
* conexão com banco;
* endpoint de saúde;
* autenticação;
* API.

Somente depois prosseguir.

## Etapa 5 — Criar Admin

Configurar:

* repositório;
* branch;
* Node;
* npm;
* build;
* variáveis;
* domínio.

Executar build e deploy.

## Etapa 6 — Criar módulos

Cada módulo deve ser configurado com:

* repositório;
* runtime;
* banco;
* URL do Core;
* variáveis;
* domínio;
* healthcheck.

## Etapa 7 — Validar integração

Testar a cadeia completa.

```text
Frontend
   ↓
Módulo
   ↓
Core
   ↓
Banco
```

---

# 17. Checklist de criação

## Cliente

* [ ] Cliente identificado.
* [ ] Ambiente TESTES criado.
* [ ] Ambiente PRODUÇÃO criado.
* [ ] Servidor definido.
* [ ] Projeto Coolify criado.

## Banco

* [ ] PostgreSQL TESTES criado.
* [ ] PostgreSQL PRODUÇÃO criado.
* [ ] Credenciais separadas.
* [ ] Banco de cada ambiente validado.

## Core

* [ ] Repositório correto.
* [ ] Branch correta.
* [ ] Java 21.
* [ ] Variáveis configuradas.
* [ ] Banco configurado.
* [ ] Flyway validado.
* [ ] Healthcheck configurado.
* [ ] Deploy realizado.

## Admin

* [ ] Repositório correto.
* [ ] Node correto.
* [ ] npm correto.
* [ ] `package.json` com runtime quando necessário.
* [ ] `npm install` validado.
* [ ] `npm run build` validado.
* [ ] Variáveis configuradas.
* [ ] Domínio configurado.
* [ ] Deploy realizado.

## Módulos

* [ ] Runtime validado.
* [ ] Core correto configurado.
* [ ] Banco correto configurado.
* [ ] Variáveis configuradas.
* [ ] Healthcheck configurado.
* [ ] Deploy validado.

---

# 18. Checklist pré-produção

Antes de liberar o cliente:

* [ ] TESTES funcionando.
* [ ] Fluxo completo validado.
* [ ] Banco de produção criado.
* [ ] Variáveis de produção configuradas.
* [ ] Secrets exclusivos.
* [ ] Domínios configurados.
* [ ] HTTPS funcionando.
* [ ] Healthchecks funcionando.
* [ ] Backups configurados.
* [ ] Logs revisados.
* [ ] Core validado.
* [ ] Admin validado.
* [ ] Módulos validados.
* [ ] Integrações validadas.

---

# 19. Duplicação para novo cliente

Uma nova instalação deve utilizar este documento como template.

Não copiar indiscriminadamente uma aplicação existente.

O processo correto é:

```text
Novo Cliente
     ↓
Duplicar estrutura de infraestrutura
     ↓
Usar os mesmos repositórios oficiais
     ↓
Criar banco novo
     ↓
Criar secrets novos
     ↓
Configurar variáveis novas
     ↓
Configurar domínios novos
     ↓
Deploy
     ↓
Validação
```

O código permanece compartilhado no GitHub.

A infraestrutura e os dados permanecem isolados.

---

# 20. Problemas conhecidos

## Node incompatível

O `sigin-admin` apresentou falha de build no Coolify utilizando Node 22.11.0.

Erro principal:

```text
Cannot find native binding
```

e:

```text
Cannot find module '@rolldown/binding-linux-x64-gnu'
```

A causa foi incompatibilidade do runtime com as versões atuais do Vite/Rolldown e outras dependências.

A solução foi definir:

```json
"engines": {
  "node": "24.x",
  "npm": "11.x"
}
```

Commit da correção:

```text
7b921ac fix(admin): definir Node 24 para build
```

### Regra

Não considerar o runtime padrão do Coolify como automaticamente compatível.

Sempre verificar o runtime exigido pelo projeto antes do deploy.

---

# 21. Diagnóstico padrão

Quando um deploy falhar:

```text
1. Identificar etapa da falha
        ↓
2. Verificar runtime
        ↓
3. Verificar dependências
        ↓
4. Verificar variáveis
        ↓
5. Verificar banco
        ↓
6. Verificar domínio/porta
        ↓
7. Verificar logs
        ↓
8. Somente então investigar código
```

Não alterar código para solucionar um problema puramente de infraestrutura.

---

# 22. Regra de documentação

Toda configuração relevante deve ser registrada.

Toda falha de infraestrutura que for resolvida deve gerar uma entrada neste documento contendo:

```text
Problema
Causa
Solução
Configuração definitiva
Commit relacionado
```

Dessa forma, uma solução aplicada a um cliente pode ser utilizada como referência para os próximos clientes.

---

# 23. Princípio final

A primeira instalação deve servir como modelo.

As próximas instalações devem ser predominantemente parametrização, e não reconstrução.

```text
UMA VEZ
Configurar
Validar
Documentar

DEPOIS
Reutilizar
Parametrizar
Validar
```

O objetivo da infraestrutura do SIGIN é permitir que um novo cliente seja provisionado de forma rápida, previsível, isolada e reproduzível, sem repetir erros ou configurações já resolvidos.

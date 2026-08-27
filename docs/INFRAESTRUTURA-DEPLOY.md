# Infraestrutura e Deploy — SIGIN

## 1. Objetivo

Este documento define o padrão de infraestrutura, build, deploy e operação dos serviços do SIGIN.

O objetivo é evitar configurações divergentes entre ambientes, reduzir retrabalho e registrar problemas de infraestrutura já identificados.

Este documento deve ser atualizado sempre que uma configuração de infraestrutura relevante for definida ou um problema de deploy for solucionado.

---

## 2. Ambientes

O SIGIN deverá possuir ambientes separados.

### Produção

Ambiente utilizado pelos usuários finais.

Características:

* Dados reais.
* Banco de dados exclusivo.
* Variáveis de ambiente próprias.
* Domínios oficiais.
* Deploy controlado.
* Não utilizar banco ou secrets de teste.

### Testes

Ambiente utilizado para validação antes da produção.

Características:

* Banco separado da produção.
* Variáveis próprias.
* Dados de teste.
* Domínio/subdomínio próprio.
* Pode receber deploys frequentes.
* Não deve compartilhar credenciais de produção.

### ROTA

Ambiente destinado aos serviços relacionados ao ROTA.

A infraestrutura poderá ser preparada antes da definição do domínio definitivo.

O domínio será configurado posteriormente, sem necessidade de reconstruir o serviço.

---

## 3. Serviços

A arquitetura deverá manter os serviços desacoplados.

### Core

Repositório:

`https://github.com/samrickbr/SIGIN`

Responsabilidade:

* API central do SIGIN.
* Autenticação.
* IAM.
* Pessoas.
* Usuários.
* Produtos.
* Categorias.
* Canais de venda.
* Pedidos e regras centrais.

Runtime:

* Java 21.
* Spring Boot.
* Maven.
* PostgreSQL 17.
* Flyway.

### Admin

Repositório:

`https://github.com/samrickbr/sigin-admin`

Responsabilidade:

* Backoffice administrativo.
* Interface web administrativa.

Runtime:

* Node.js 24.x.
* npm 11.x.
* React.
* Vite.
* TypeScript.

### Delivery Back

Repositório:

`https://github.com/samrickbr/sigin-delivery`

Responsabilidade:

* Backend específico do Delivery.
* Integração com o SIGIN Core.

Runtime:

* Java 21.
* Spring Boot.
* Maven.
* PostgreSQL.
* RestClient.

### Delivery Front

Repositório:

`https://github.com/samrickbr/delivery-front`

Responsabilidade:

* Interface do Delivery.
* Cardápio.
* Identificação do cliente.
* Checkout.
* Envio e acompanhamento do pedido.

Runtime:

* Node.js.
* npm.
* React.
* Vite.
* TypeScript.

A versão efetiva do Node deverá ser definida no próprio repositório e respeitada pelo ambiente de deploy.

---

## 4. Regra fundamental de Runtime

A versão utilizada localmente deve ser compatível com a versão utilizada no ambiente de produção/testes.

Não assumir que o runtime padrão do Coolify/Nixpacks é adequado ao projeto.

Cada frontend deverá declarar sua versão mínima/esperada de Node e npm em `package.json` quando necessário.

Exemplo adotado pelo SIGIN Admin:

```json
"engines": {
  "node": "24.x",
  "npm": "11.x"
}
```

Essa configuração deve acompanhar o projeto no Git.

---

## 5. Problema conhecido — Node/Vite/Rolldown

### Sintoma

O build do `sigin-admin` falhou no Coolify com:

```text
Cannot find native binding
```

e:

```text
Cannot find module '@rolldown/binding-linux-x64-gnu'
```

O ambiente estava utilizando:

```text
Node.js v22.11.0
```

Enquanto as versões atuais utilizadas pelo projeto exigiam uma versão mais recente do Node.

O log também apresentou incompatibilidades de engine para:

* Vite.
* Rolldown.
* @vitejs/plugin-react.
* ESLint.
* pacotes relacionados ao ESLint.

### Causa

O ambiente de build estava utilizando Node 22.11.0, enquanto as dependências atuais do projeto exigiam Node compatível com versões superiores.

### Solução adotada

O `package.json` do `sigin-admin` passou a declarar:

```json
"engines": {
  "node": "24.x",
  "npm": "11.x"
}
```

Commit:

```text
7b921ac fix(admin): definir Node 24 para build
```

Após o ajuste, o deploy no Coolify foi concluído com sucesso.

### Regra

Antes de investigar dependências, downgrade de bibliotecas ou alterações no código, verificar:

1. Versão do Node local.
2. Versão do Node no ambiente de deploy.
3. Versão do npm.
4. Engines declarados pelas dependências.
5. `package.json`.
6. `package-lock.json`.

Não fazer downgrade de Vite/Rolldown apenas para contornar incompatibilidade de runtime.

---

## 6. Gerenciamento de dependências

O `package-lock.json` deve permanecer versionado no Git.

Após alterações de dependências:

```bash
npm install
```

deve ser executado localmente.

Depois:

```bash
npm run build
```

deve ser validado antes do push.

### Regra importante

Não alterar manualmente o `package-lock.json` para corrigir um problema de ambiente.

Quando uma alteração de lockfile ocorrer naturalmente durante a instalação, verificar o diff antes de realizar o commit.

---

## 7. Build dos Frontends

O build deve ser reproduzível localmente e no ambiente de deploy.

Para o SIGIN Admin:

```bash
npm install
npm run build
```

O script oficial é:

```json
"build": "tsc -b && vite build"
```

O build precisa concluir sem erro antes do deploy.

---

## 8. Build dos Backends

Backends Java devem utilizar:

```text
Java 21
Maven
Spring Boot
```

O projeto deve fornecer um processo de build reproduzível.

Antes do deploy:

```bash
mvn clean verify
```

ou o comando equivalente definido pelo projeto.

---

## 9. Variáveis de ambiente

Configurações específicas de ambiente não devem ser fixadas no código.

Devem ser configuráveis por ambiente.

Exemplos:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
CORE_URL
API_URL
JWT_SECRET
```

Os nomes efetivos devem ser definidos pelo próprio serviço.

### Regra

Produção e testes devem possuir valores diferentes.

Nunca reutilizar:

* senha de banco de produção em testes;
* JWT secret de produção em testes;
* banco de produção em testes;
* credenciais de serviços externos de produção em testes.

---

## 10. Bancos de dados

Cada ambiente deve possuir banco isolado.

Exemplo:

```text
PostgreSQL
├── produção
└── testes
```

Os serviços que necessitam de banco devem possuir configuração própria para cada ambiente.

Migrations devem ser controladas pelo mecanismo adotado pelo projeto.

No Core:

```text
Flyway
```

Não executar migrations manualmente em produção sem verificar previamente o estado do banco e a estratégia definida pelo projeto.

---

## 11. Coolify

O Coolify é responsável pela implantação dos serviços.

Para cada aplicação devem ser configurados:

* Git repository.
* Branch.
* Build method.
* Runtime.
* Build command.
* Start command.
* Environment variables.
* Domain.
* Healthcheck.
* Server.
* Resource limits quando necessário.

A configuração deve seguir o padrão definido neste documento.

---

## 12. Domínios

Cada ambiente deverá possuir seus próprios endereços.

Exemplo conceitual:

```text
Produção
├── admin.seudominio
├── api.seudominio
└── delivery.seudominio

Testes
├── admin-test.seudominio
├── api-test.seudominio
└── delivery-test.seudominio
```

Os nomes definitivos serão definidos durante a configuração do ambiente.

### ROTA

O serviço ROTA pode ser criado antes da aquisição/configuração do domínio definitivo.

Nesse caso:

1. Criar a aplicação no Coolify.
2. Configurar build.
3. Configurar runtime.
4. Configurar variáveis.
5. Fazer deploy.
6. Validar funcionamento.
7. Configurar o domínio posteriormente.

A troca do domínio não deve exigir alteração no código da aplicação.

---

## 13. Healthcheck

Serviços publicados devem possuir healthcheck sempre que suportado pela aplicação.

O healthcheck deve validar se a aplicação está realmente disponível.

Não considerar um container iniciado como sinônimo de aplicação saudável.

---

## 14. Processo padrão de implantação

Todo novo serviço deverá seguir esta sequência:

```text
1. Repositório
2. Verificar runtime
3. Verificar dependências
4. Configurar variáveis
5. Executar build local
6. Commit
7. Push
8. Criar aplicação no Coolify
9. Configurar runtime
10. Configurar variáveis
11. Configurar domínio
12. Configurar healthcheck
13. Deploy
14. Verificar logs
15. Testar aplicação
16. Registrar configuração
```

---

## 15. Checklist pré-deploy

* [ ] Working tree limpo.
* [ ] Branch correta.
* [ ] Commit criado.
* [ ] Push realizado.
* [ ] Repositório sincronizado com `origin`.
* [ ] Runtime local compatível com o projeto.
* [ ] Runtime do Coolify configurado.
* [ ] Dependências instaladas sem erro.
* [ ] Build local concluído.
* [ ] Variáveis de ambiente revisadas.
* [ ] Banco correto configurado.
* [ ] Domínio correto configurado.
* [ ] Healthcheck definido.

---

## 16. Checklist pós-deploy

* [ ] Deploy concluído.
* [ ] Container em execução.
* [ ] Runtime correto confirmado nos logs.
* [ ] Sem erros de build.
* [ ] Sem erros de inicialização.
* [ ] Healthcheck aprovado.
* [ ] Domínio respondendo.
* [ ] API/frontend acessível.
* [ ] Integrações testadas.
* [ ] Logs revisados.

---

## 17. Diagnóstico de falha de deploy

Quando um deploy falhar, seguir esta ordem:

### 1. Identificar a etapa

Verificar se falhou em:

```text
Clone
↓
Install
↓
Build
↓
Start
↓
Healthcheck
```

### 2. Confirmar runtime

Verificar:

```text
Node
npm
Java
Maven
```

### 3. Confirmar configuração

Verificar:

```text
Environment Variables
Domain
Port
Build Command
Start Command
```

### 4. Confirmar dependências

Verificar:

```text
package.json
package-lock.json
pom.xml
```

### 5. Somente depois investigar código

Problemas de infraestrutura não devem ser tratados inicialmente como bugs de aplicação.

---

## 18. Regra para novos containers

Não configurar cada aplicação do zero sem consultar este documento.

Ao criar um novo serviço:

1. Identificar a tecnologia.
2. Identificar a versão do runtime.
3. Identificar as variáveis obrigatórias.
4. Identificar dependências externas.
5. Reutilizar o padrão de infraestrutura.
6. Alterar somente o que for específico daquele serviço.
7. Registrar novas particularidades neste documento.

---

## 19. Regra contra retrabalho

Toda correção de infraestrutura que resolver um problema real deve ser documentada.

Exemplo:

```text
Problema
→ causa
→ solução
→ configuração definitiva
→ commit
```

O objetivo é transformar problemas resolvidos em conhecimento operacional permanente.

---

## 20. Estado atual

### SIGIN Admin

Status:

```text
BUILD LOCAL: OK
DEPLOY COOLIFY: OK
NODE: 24.x
NPM: 11.x
```

Commit responsável pela correção do runtime:

```text
7b921ac
fix(admin): definir Node 24 para build
```

### Próximas estruturas

```text
TESTES
├── SIGIN Core
├── SIGIN Admin
├── Delivery Back
├── Delivery Front
└── PostgreSQL

ROTA
├── Rota Back
├── Rota Front
└── PostgreSQL, quando necessário
```

A infraestrutura de testes deve permanecer isolada da produção.

A infraestrutura do ROTA deve ser preparada de forma independente do domínio definitivo.

---

## 21. Princípio operacional

O ambiente de desenvolvimento, o Git e o ambiente de deploy devem representar a mesma aplicação e as mesmas premissas técnicas.

Sempre que houver diferença entre eles, a diferença deve ser identificada, corrigida ou documentada antes de considerar o deploy concluído.

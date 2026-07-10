# 🚀 SIGIN

> **Sistema Integrado de Gestão da INOVA Studio 3D**

O SIGIN é um ERP desenvolvido para atender as necessidades da **INOVA Studio 3D**, centralizando o gerenciamento de produtos, estoque, produção, movimentações, vendas, parceiros e indicadores de desempenho.

O projeto nasceu da necessidade de substituir planilhas por um sistema robusto, escalável e totalmente automatizado.

---

# 🎯 Objetivo

Desenvolver um sistema web moderno capaz de controlar todo o fluxo operacional da empresa, desde a produção até a venda final, mantendo rastreabilidade completa de todas as movimentações de estoque.

---

# 🛠 Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL 17
- Flyway
- Maven
- Swagger / OpenAPI
- Lombok

---

# 📦 Funcionalidades

## ✅ Concluído

### Produtos

- Cadastro de produtos
- Atualização de produtos
- Consulta por ID
- Listagem de produtos
- Exclusão lógica
- Geração automática de código
- Tratamento global de exceções
- API REST documentada via Swagger

### Categorias

- Cadastro
- Consulta
- Associação com produtos

---

## 🚧 Em desenvolvimento

### Estoque

- Controle automático por movimentações
- Consulta de saldo
- Histórico de estoque

### Produção

- Entrada automática no estoque
- Controle por lote

### Movimentações

- Produção
- Transferência
- Venda
- Perda
- Ajuste

### Parceiros

- Controle de locais
- Estoque por parceiro

### Dashboard

- Indicadores
- Produtos mais vendidos
- Estoque mínimo
- Gráficos

### Financeiro

- Receitas
- Despesas
- Lucro
- Fluxo de caixa

---

# 🏗 Arquitetura

```
Controller
        │
        ▼
Service
        │
        ▼
Repository
        │
        ▼
PostgreSQL
```

Cada módulo possui sua própria estrutura:

```
produto
├── controller
├── dto
├── entity
├── repository
└── service
```

---

# 📂 Estrutura do projeto

```
src
└── main
    ├── java
    │   └── br.com.inova.sigin
    │       ├── produto
    │       └── shared
    │
    └── resources
        ├── application.yml
        └── db
            └── migration
```

---

# 📚 API

Após iniciar a aplicação:

http://localhost:8080/swagger-ui.html

---

# 🚀 Como executar

## Clone

```bash
git clone https://github.com/samrickbr/SIGIN.git
```

## Entre no projeto

```bash
cd SIGIN
```

## Configure o PostgreSQL

Crie um banco chamado:

```
sigin
```

Configure o arquivo:

```
application.yml
```

## Execute

```bash
mvn spring-boot:run
```

ou execute a classe

```
SiginApplication
```

---

# 🗺 Roadmap

- [x] Cadastro de Produtos
- [x] Cadastro de Categorias
- [x] CRUD completo de Produtos
- [x] Geração automática de código
- [ ] Cadastro de Parceiros
- [ ] Movimentações de Estoque
- [ ] Controle de Produção
- [ ] Estoque automático
- [ ] Dashboard
- [ ] Financeiro
- [ ] Controle de Usuários

---

# 💡 Motivação

O projeto surgiu durante o desenvolvimento da operação da **INOVA Studio 3D**, buscando substituir controles em planilhas por um sistema profissional, preparado para crescer junto com a empresa.

---

# 👨‍💻 Desenvolvedor

**Ricardo ("Rick")**

Projeto desenvolvido para estudo, evolução profissional e utilização real na operação da INOVA Studio 3D.

---

# 📄 Licença

Projeto em desenvolvimento.
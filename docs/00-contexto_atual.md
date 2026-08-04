Contexto de abertura da Sprint X
1. Estado atual do projeto
   Versão/tag atual:
   Ex: v0.5.0
   Última sprint concluída:
   Sprint 04 — Fundação Comercial (Core)
   Status:
   Concluída
2. Objetivo da nova Sprint

Exemplo:

Sprint 05 — Financeiro Inicial

Objetivo:
Criar base financeira integrada ao pedido e vendas.
3. Arquitetura atual (sempre manter)
   Tecnologias

Java 21
Spring Boot 4
Spring Data JPA
PostgreSQL 17
Flyway
Maven
Swagger/OpenAPI
Lombok

Estrutura:

br/com/inova/sigin

modulo
├── controller
├── dto
├── entity
├── mapper
├── repository
└── service
4. Últimas decisões arquiteturais

Esse é o mais importante.

Exemplo da Sprint 04:

ProdutoVenda:
- preço pertence ao Core
- cliente nunca envia valor unitário

ProdutoCanal:
- controla permissão de venda

CanalVenda:
- origem comercial do pedido

Na próxima sprint, colocaríamos as decisões novas aqui.

5. Estado dos módulos existentes

Uma lista simples:

Produto          ✅ estável
Pessoa           ✅ estável
Estoque          ✅ estável
Produção         ✅ estável
Pedido           ✅ estável
ProdutoVenda     ✅ atualizado Sprint 04
Delivery         🟡 em evolução
Financeiro       ⏳ não iniciado
6. Migrations atuais

Não precisa mandar todas.

Só:

Última migration aplicada:

V29__add_canal_origem_pedidos.sql

E novas migrations começam da próxima:

V30__
7. Documentação atualizada

Lista:

docs/
├── 02-arquitetura.md
├── 04-sprints.md
├── 04.01_tasks_sprint_04.md
├── 07-integracao-delivery.md
└── DEV-SEEDERS.md
8. Pendências conhecidas

Exemplo:

- Revisar ProdutoVenda x ProdutoCanal futuramente
- Melhorar testes automatizados
- Criar documentação API pública
9. Regra de trabalho

Manter o padrão:

Planejar Sprint
↓
Criar Tasks
↓
Implementar
↓
Testar endpoints
↓
Atualizar documentação
↓
Commit
↓
Tag
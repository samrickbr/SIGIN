## Regra de definição: Core x Módulo

Um componente pertence ao Core quando:

Ele representa um conceito fundamental do sistema e outros módulos dependem dele.

Caso contrário, pertence a um módulo específico.

Core

Contém entidades e serviços compartilhados.

Critério:

Módulo A
Módulo B
Módulo C
|
↓
CORE

O Core não depende dos módulos.

Exemplos:

Cadastros

✅ Core

Porque praticamente tudo depende de:

Produto
Cliente
Fornecedor
Unidade
Categoria
Usuário
Empresa

Exemplo:

Estoque
|
Venda
|
Financeiro
|
Relatórios

todos usam Produto

Então:

Produto → Core
Usuários e permissões

✅ Core

Porque:

Venda
Estoque
Financeiro
Produção

todos precisam saber:
"quem fez?"
"quem pode acessar?"
CanalVenda

Após essa regra:

✅ Core

Porque futuramente:

Venda
Delivery
Marketplace
Integrações
Relatórios
Estoque reservado

podem depender dele.

Módulos

Um módulo deve conter uma regra de negócio específica.

Exemplo:

Venda
Pedido
Carrinho
Pagamento
Desconto comercial
Cupom

Depende:

Venda
|
+-- Produto (Core)
+-- Cliente (Core)
+-- CanalVenda (Core)

Mas:

Core
X
Venda

Nunca deve acontecer.

Relatórios

Aqui entra um ponto importante.

Não devemos criar "Relatório" como um bloco único.

A regra fica:

Relatórios estruturais

Core:

Relatório de:
- Produtos cadastrados
- Clientes
- Usuários
- Auditoria

Porque são dados base.

Relatórios de negócio

Módulo:

Exemplo:

Venda:
- Faturamento por período
- Ticket médio
- Produtos vendidos
- Margem

Estoque:
- Giro de estoque
- Curva ABC
- Ruptura

Financeiro:
- Fluxo de caixa
- Contas vencidas
  Dependência correta do SIGIN

A arquitetura fica:
                CORE
Produto
Cliente
Usuário
Empresa
CanalVenda
Permissões
Auditoria
        ↑        ↑        ↑
      Venda   Estoque  Financeiro
        ↓
Relatórios específicos
Nunca:
Estoque → Venda ❌
Venda → Financeiro ❌
Core → Estoque ❌

---
## Definidos

✅ CanalVenda = Core
SIGIN — Princípios Arquiteturais Consolidados

Versão: 1.0
Status: Documento Permanente do Projeto

Objetivo

Este documento define as decisões arquiteturais permanentes do SIGIN.

Seu objetivo é impedir mudanças de direção desnecessárias, preservar a evolução incremental do sistema e servir como referência obrigatória antes de qualquer refatoração estrutural.

Toda nova Sprint deve respeitar estes princípios.

1. O Core é o centro do sistema

O SIGIN é composto por um único Core responsável pelas regras de negócio.

Nenhum módulo externo implementa regras comerciais próprias.

Todos os módulos consomem o Core.

Exemplos:

Delivery
PDV
Comanda
Catálogo Online
Aplicativo
Marketplace
APIs futuras

Todos são consumidores do Core.

2. O Core é o dono das regras de negócio

Toda regra pertence ao Core.

Exemplos:

Produtos
Pessoas
Estoque
Financeiro
Produção
Pedidos
Canais de Venda
Formas de Pagamento
Fluxo de Produção
Reserva de Estoque

Módulos externos apenas apresentam a experiência ao usuário.

3. Nunca recriar funcionalidades existentes

Antes de criar qualquer:

Entity
Service
Repository
Controller
DTO
Migration

é obrigatório verificar se já existe implementação equivalente.

Caso exista:

Evoluir a implementação existente.

Nunca recriar apenas por preferência arquitetural.

4. Evolução incremental

O SIGIN não será reescrito.

Toda evolução deve aproveitar a estrutura existente.

A regra é:

Evoluir sempre. Reescrever apenas quando tecnicamente inevitável.

5. Refatoração exige justificativa

Uma proposta de alteração arquitetural somente pode ser aceita quando demonstrar claramente:

problema atual
benefício obtido
impacto da mudança
plano de migração
compatibilidade com o restante do sistema

Caso contrário:

A estrutura existente deve ser mantida.

6. Ordem de desenvolvimento

Toda Sprint segue obrigatoriamente esta sequência:

Planejamento

↓

Implementação

↓

Testes

↓

Documentação

↓

Commit

↓

Push

↓

Roadmap atualizado

Nunca inverter esta ordem.

7. Roadmap é a fonte da verdade

O Roadmap define:

prioridades
próximas Sprints
decisões arquiteturais
direção do projeto

Nenhuma Sprint pode alterar a direção do sistema sem atualizar primeiro o Roadmap.

8. Desenvolvimento por Sprint

Cada Sprint possui apenas um objetivo principal.

Ao concluir:

documentação
validação
testes
commit
push

A próxima Sprint inicia em um novo chat.

O Roadmap permanece como histórico oficial da evolução.

9. Estruturas consolidadas não devem ser remodeladas

Os módulos abaixo são considerados consolidados.

Sua evolução deve ser incremental.

Pessoas

Cadastro único.

Pessoa pode assumir múltiplos papéis através de PessoaTipo.

Não criar novos cadastros separados de Cliente, Funcionário, Fornecedor etc.

Produtos

Cadastro único.

A disponibilidade para cada módulo é definida por Canal de Venda.

Nunca criar produtos específicos para Delivery ou PDV.

Pedido

Pedido é uma entidade comercial do Core.

Delivery, PDV e Comanda apenas originam pedidos.

Não criar pedidos separados para cada módulo.

Estoque

Existe apenas um estoque.

Toda movimentação deve passar pelo módulo de estoque.

Financeiro

O financeiro nasce a partir dos eventos comerciais.

Não existem movimentações financeiras independentes do Core.

10. Delivery é um consumidor

O Delivery possui interface própria.

Porém:

não possui regra comercial própria
não possui produtos próprios
não possui pedidos próprios
não possui produção própria

Ele apenas consome a API do Core.

11. Canais de Venda

Produtos podem estar disponíveis em um ou mais canais.

Exemplos:

Delivery
PDV
Comanda
Cardápio Online
Marketplace

A disponibilidade é configurada no Core.

Nunca por regra fixa no módulo consumidor.

12. Arquitetura orientada à expansão

Toda implementação deve considerar que novos módulos poderão existir.

Exemplos futuros:

App Android
App iOS
Portal do Cliente
Portal do Fornecedor
Marketplace
Integrações externas

Nenhuma regra pode assumir que existe apenas o Delivery.

13. Compatibilidade é prioridade

Sempre priorizar:

compatibilidade
reaproveitamento
baixo impacto
evolução incremental

Evitar grandes refatorações sem necessidade.

14. Antes de propor mudanças

Antes de sugerir uma alteração arquitetural, responder obrigatoriamente:

Essa funcionalidade já existe?
Posso evoluí-la em vez de recriá-la?
A mudança quebra alguma Sprint anterior?
Existe ganho técnico real?
O Roadmap continua coerente?

Se qualquer resposta indicar risco desnecessário, a estrutura existente deve ser preservada.

15. Visão de longo prazo

O SIGIN evoluiu de um ERP para produção 3D para uma plataforma ERP modular.

A arquitetura atual foi construída de forma incremental e validada ao longo das Sprints.

O objetivo é continuar expandindo essa base, mantendo estabilidade, coerência e reutilização, evitando reconstruções desnecessárias.

Princípio Fundamental

Evoluir sempre. Reescrever apenas quando for tecnicamente inevitável.

16. Decisões
As decisões arquiteturais pertencem ao Roadmap. Cada Sprint executa essas decisões.
O Roadmap é o espaço para discutir alternativas, modelar soluções e tomar decisões. A Sprint não deve reabrir discussões arquiteturais já consolidadas; seu foco é implementar, testar, documentar e validar o que foi definido. Isso mantém cada chat objetivo e evita mudanças de direção durante a execução.

convenção já adotada no projeto?
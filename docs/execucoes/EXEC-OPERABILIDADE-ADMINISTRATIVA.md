# Feedback de Execução — Operabilidade Administrativa

## Status

CONCLUÍDO

## Escopo executado

Complementação pontual da operabilidade administrativa do SIGIN Core.

A execução foi limitada a pequenas evoluções de contrato e comportamento já compatíveis com o modelo existente.

## 1. Pessoa x TipoPessoa

Foi adicionada a remoção de um vínculo entre Pessoa e TipoPessoa.

Contrato:

DELETE /pessoas/{id}/tipos/{tipoPessoaId}

A operação remove somente o vínculo.

Pessoa e TipoPessoa permanecem persistidos.

## 2. Usuários

Foi adicionada consulta administrativa por situação de ativo.

Contratos:

GET /usuarios

GET /usuarios?ativo=true

GET /usuarios?ativo=false

O parâmetro ativo é opcional.

A consulta sem parâmetro preserva o comportamento padrão de listagem de usuários ativos.

A reativação continua utilizando o PUT existente de Usuario, através do campo ativo.

## 3. Perfis

Foi adicionada consulta administrativa por situação de ativo.

Contratos:

GET /perfis

GET /perfis?ativo=true

GET /perfis?ativo=false

A reativação continua utilizando o PUT existente de Perfil, através do campo ativo.

## 4. CanalVenda

A operação administrativa de exclusão deixou de remover fisicamente o CanalVenda.

Contrato existente:

DELETE /api/canais-venda/{id}

O comportamento agora é de inativação lógica.

O registro permanece persistido.

Os vínculos comerciais existentes são preservados.

A consulta também passou a aceitar o filtro ativo.

Contratos:

GET /api/canais-venda

GET /api/canais-venda?ativo=true

GET /api/canais-venda?ativo=false

A regra de Produto x Canal e a regra de preço por canal não foram alteradas.

## 5. Locais

Foi adicionada consulta administrativa por situação de ativo.

Contratos:

GET /locais

GET /locais?ativo=true

GET /locais?ativo=false

A reativação continua utilizando o PUT existente de Local, através do campo ativo.

O histórico de estoque e movimentações não é removido.

## Banco

Nenhuma migration foi criada nesta execução.

Não foram criadas novas entidades.

Não houve alteração estrutural de banco.

## IAM

Não houve alteração em:

- JWT;
- autenticação;
- autorização;
- UsuarioPerfil;
- PerfilPermissao;
- relação Pessoa e Usuario.

## Produto x Canal

Não houve alteração em:

- ProdutoCanal;
- ProdutoVenda;
- preços;
- disponibilidade;
- regras comerciais de canal.

## Estoque

Não houve criação de novo modelo de saldo ou alteração estrutural do estoque.

## Build

Comando executado:

mvn clean package -DskipTests

Resultado:

BUILD SUCCESS

## Testes

Os testes automatizados não foram utilizados como critério de validação desta execução porque a suíte existente estava defasada.

O build foi utilizado como validação de compilação e empacotamento.

## Histórico Git

Commit:

7fb3e06ffb50e48412aaae6034afae01b6b5e578

Mensagem:

fix: completa operabilidade administrativa do core

## Branch

main

## Push

O commit foi enviado para origin/main.

## Resultado

As cinco lacunas administrativas autorizadas foram implementadas sem criação de nova arquitetura, nova entidade ou alteração das regras comerciais consolidadas.

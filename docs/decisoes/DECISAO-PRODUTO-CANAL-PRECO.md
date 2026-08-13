# Decisão Arquitetural — Produto x Canal x Preço

## Contexto

O SIGIN suporta múltiplos canais de venda utilizando o mesmo cadastro de Produto.

Cada canal pode possuir características comerciais próprias.

## Decisão

Produto possui um preço global ou padrão.

Cada CanalVenda ou módulo que comercializa o Produto pode possuir um preço específico.

A existência de preço específico na relação comercial entre Produto e Canal é intencional.

O preço específico não deve ser substituído por um único preço global.

## Modelo conceitual

Produto
ProdutoCanal
ProdutoVenda

ProdutoCanal controla a disponibilidade comercial do Produto no canal.

ProdutoVenda representa os dados comerciais utilizados na venda, incluindo o preço específico quando aplicável ao modelo atual.

## Regra

O cliente ou módulo consumidor não deve assumir a responsabilidade pela definição das regras comerciais do preço.

O Core permanece responsável pelas regras comerciais.

Esta decisão não deve ser alterada sem nova decisão arquitetural do Roadmap Core.

## Status

APROVADA

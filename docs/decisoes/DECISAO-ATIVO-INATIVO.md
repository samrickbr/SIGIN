# Decisão Arquitetural — Ativo / Inativo

## Contexto

Diversos cadastros administrativos do SIGIN possuem o conceito de ativo/inativo.

A manutenção administrativa precisa preservar os registros sem permitir que registros inativos sejam utilizados indevidamente em novos fluxos.

## Decisão

O padrão consolidado é:

ATIVO

Disponível para novos fluxos conforme as regras do domínio.

INATIVO

Permanece persistido.

Não deve ser utilizado em novos fluxos quando a regra do domínio impedir.

Continua disponível para administração.

Pode ser reativado quando permitido pelo domínio.

## Operação preferencial

Quando o recurso já possui atualização por identificador, a preferência arquitetural é utilizar:

PUT /recurso/{id}

com:

ativo = true

ou:

ativo = false

Evitar a criação indiscriminada de endpoints específicos como ativar e inativar.

## Consulta administrativa

Quando necessário para manutenção, o Core deve permitir que registros inativos sejam localizados administrativamente.

A forma exata da consulta pode variar conforme o contrato existente de cada recurso.

Esta decisão não afirma que todas as entidades possuem exatamente o mesmo comportamento.

Exceções de domínio devem permanecer explícitas.

## Status

APROVADA

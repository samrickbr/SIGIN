# F08 — Temas Retornados ao Roadmap

## Status

**RETORNADO AO ROADMAP — NÃO IMPLEMENTADO NA F08**

## 1. Política geral de reativação

Definir futuramente uma política sistêmica para:

- registros ativos;
- registros inativos;
- exclusão lógica;
- reativação;
- permissões;
- localização da funcionalidade administrativa.

Direção levantada:

```
Manutenção
└── Registros Inativos
    └── Reativar
```

Esta direção não foi implementada na F08.

2. Unicidade + Soft Delete

Definir uma política sistêmica para situações em que:

existe um registro inativo;
ocorre uma nova tentativa de cadastro com a mesma chave natural.

Avaliar futuramente:

se o registro inativo continua ocupando a unicidade;
quando deve ocorrer reutilização;
quando deve ocorrer novo cadastro;
quando deve ocorrer reativação;
como preservar histórico.

O comportamento de ProdutoMaterial consolidado na F08 não deve ser generalizado automaticamente para outras entidades.

O caso de Local permanece como evidência de uma questão sistêmica a ser analisada pelo Roadmap.

Nenhuma alteração em Local foi realizada nesta demanda.

3. Registros Inativos

Avaliar futuramente uma experiência administrativa própria para:

consultar registros inativos;
identificar registros anteriormente cadastrados;
reativar registros quando permitido.

Este tema não foi implementado na F08.

4. Organização dos menus administrativos

Avaliar como evolução futura do Front/Roadmap uma organização dos recursos administrativos em estruturas como:

Recurso
├── Cadastro
├── Manutenção
├── Registros Inativos
└── Outras operações

Objetivo:

evitar crescimento descontrolado do menu principal.

Nenhuma alteração de Front foi realizada na F08.

5. Gerador de código de Material

Registrar como melhoria futura a avaliação de um mecanismo de geração de código de Material.

Questões ainda não decididas:

formato;
prefixo;
sequência;
unicidade;
edição manual;
reutilização;
responsabilidade do Core;
concorrência;
persistência.

Nenhuma implementação foi realizada na F08.

6. Suíte de testes

A suíte automatizada existente encontra-se defasada em relação ao estado atual do Core.

A atualização e adequação dos testes devem ser tratadas como demanda própria.

O mvn test não foi executado na F08 por essa razão.

7. Regra de escopo

Os temas deste documento são retornos ao Roadmap.

Eles:

não fazem parte da F08;
não foram implementados;
não alteram as decisões consolidadas da F08;
não autorizam alterações futuras sem nova decisão/escopo do Roadmap.
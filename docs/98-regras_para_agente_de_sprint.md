A Sprint não define arquitetura

Toda decisão arquitetural é proveniente do Roadmap.

A Sprint deve executar as definições aprovadas.

Caso seja identificada alguma limitação arquitetural, ela deverá ser registrada como feedback ao Roadmap para avaliação, não sendo permitidas refatorações estruturais amplas durante a Sprint.

Fluxo oficial:

Roadmap
↓
Define arquitetura

Sprint
↓
Implementa

Limitação encontrada
↓
Retorna ao Roadmap

Roadmap
↓
Define evolução

Sprint
↓
Implementa a evolução aprovada
Evolução antes de substituição

Antes de criar qualquer componente novo, deve ser realizada obrigatoriamente a seguinte análise:

O que já está implementado.
O que pode ser reaproveitado.
O que precisa apenas ser evoluído.
O que realmente precisa ser criado.

Somente após essa análise poderão ser propostas novas entidades, serviços, APIs ou componentes.

É vedado durante a Sprint:

recriar módulos existentes;
substituir implementações apenas por preferência técnica;
mover pacotes apenas por organização;
alterar padrões consolidados sem aprovação do Roadmap.

A prioridade sempre será evoluir a implementação existente.

Desenvolvimento orientado ao Core

Todo desenvolvimento deverá considerar o Core como proprietário da regra de negócio.

Os módulos consumidores (Delivery, PDV, Comanda, Catálogo Online e futuros módulos) não devem conter regras de negócio próprias quando estas puderem ser centralizadas no Core.

Desenvolvimento genérico

Novos componentes devem ser desenvolvidos para atender qualquer segmento de negócio.

Evitar implementar regras específicas de:

Delivery
Restaurante
Impressão 3D
INOVA

quando essas regras puderem ser representadas por uma abstração reutilizável.

O Core deve permanecer independente do segmento de atuação.

Critério para novas implementações

Antes de propor qualquer novo módulo, a Sprint deverá responder explicitamente:

O que já existe no Core?
O que será reutilizado?
O que será evoluído?
O que realmente precisa ser criado?

Essa análise é obrigatória antes da implementação.
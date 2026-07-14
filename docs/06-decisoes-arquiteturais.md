## DA-007 — Reserva Automática de Estoque

**Data:** 13/07/2026

### Decisão

Toda Ordem de Produção deve reservar automaticamente os materiais necessários no momento de sua criação.

### Motivação

Evitar que múltiplas Ordens de Produção utilizem o mesmo estoque disponível, garantindo maior confiabilidade no planejamento da produção.

### Consequências

- Separação entre estoque físico e estoque disponível.
- Base para implementação futura de MRP.
- Preparação para integração com pedidos e planejamento da produção.

## Pedido x Ordem de Produção

O Pedido não cria automaticamente uma Ordem de Produção.

A integração será realizada na Sprint 17 através de um serviço específico de conversão Pedido → Ordem de Produção, preservando o desacoplamento entre os módulos Comercial e Produção.


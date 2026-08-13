# SIGIN

Sistema Integrado de Gestão.

## Visão geral

O SIGIN possui um Core responsável pelo domínio, regras de negócio, persistência e contratos de API, consumido pelo Front Administrativo e pelos módulos operacionais.

## Arquitetura

O Core fornece APIs para o Front Administrativo e para os módulos consumidores.

O Core é responsável por:

- domínio;
- regras de negócio;
- persistência;
- contratos;
- autenticação;
- autorização;
- infraestrutura compartilhada.

O Front é responsável pela experiência administrativa.

## Documentação

- [Documentação do SIGIN Core](docs/README.md)
- [Processo de documentação](docs/processo-documentacao.md)
- [Decisões arquiteturais](docs/decisoes/)
- [Sprints](docs/sprints/)
- [Execuções](docs/execucoes/)
- [Templates](docs/templates/)

## Princípio de evolução

- Roadmap define arquitetura e escopo.
- Executor implementa somente o que foi autorizado.
- Testes e validações confirmam o resultado.
- Documentação registra o estado consolidado.
- Alterações arquiteturais não devem ser introduzidas incidentalmente durante uma Sprint.

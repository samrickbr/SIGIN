# Decisão Arquitetural — IAM / Usuario

## Contexto

O SIGIN separa identidade de negócio de identidade de acesso.

Pessoa representa a identidade de negócio do sistema.

Usuario representa a identidade utilizada para acesso ao SIGIN.

## Decisão

A relação consolidada de identidade e autorização é:

Pessoa
Usuario
UsuarioPerfil
Perfil
PerfilPermissao
Permissao

Nem toda Pessoa possui Usuario.

Todo Usuario pertence a uma Pessoa.

Pessoa não depende de Usuario para representar sua identidade de negócio.

Não existe uma entidade Funcionário separada. Quando uma pessoa exerce a função de funcionário, isso é representado por Pessoa associada ao TipoPessoa correspondente.

Usuario não recebe Permissao diretamente.

A autorização é derivada da associação:

Usuario
UsuarioPerfil
Perfil
PerfilPermissao
Permissao

## Responsabilidade do IAM

O módulo Usuario/IAM é infraestrutura do Core.

Suas responsabilidades incluem identidade de acesso, autenticação, autorização, perfis e permissões.

Os domínios comerciais consomem o IAM.

O IAM não deve depender dos domínios comerciais para funcionar.

## Status

APROVADA

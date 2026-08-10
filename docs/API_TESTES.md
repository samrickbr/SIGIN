Autenticação — /auth/me

O endpoint /auth/me fornece a identidade do usuário autenticado a partir do token JWT enviado na requisição.

Endpoint

GET /auth/me

Autenticação

Requer token JWT no header:

Authorization: Bearer <token>
Resposta autenticada

Com um token válido, o endpoint retorna:

dados básicos do usuário;
pessoa vinculada;
perfis;
permissões.

Exemplo:

{
"id": 1,
"login": "Rick",
"ativo": true,
"pessoa": {
"id": 4,
"nome": "Rick",
"tipoDocumento": "RG",
"documento": "0123456789",
"telefone": "47988042230",
"email": "ricrdocardoso@gmail.com",
"observacao": "string",
"ativo": true,
"dataCriacao": "2026-08-04T13:11:16.136294",
"tipos": [
"FUNCIONARIO"
]
},
"perfis": [
{
"id": 1,
"nome": "Administrador",
"descricao": "ADM do sistema",
"ativo": true
}
],
"permissoes": [
{
"id": 1,
"codigo": "PRODUTO_VISUALIZAR",
"descricao": "Permite visualizar produtos teste",
"ativo": false
}
]
}
Validação realizada no Swagger
Cenário	Resultado
Token JWT válido	200 OK
Sem token	403 Forbidden
Token inválido	403 Forbidden

O endpoint está confirmado como contrato de identidade autenticada para consumidores do SIGIN, incluindo o Front Administrativo.

Observação

O endpoint não cria um novo mecanismo de autenticação. Ele utiliza a autenticação JWT já existente no Core e apenas expõe a identidade do usuário autenticado.


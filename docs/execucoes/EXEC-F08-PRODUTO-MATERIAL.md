# F08 — Produto × Material — Encerramento

## 1. Solicitação

Implementar a manutenção de vínculos entre Produto e Material no Core, incluindo:

- criação de vínculo;
- atualização de quantidade e situação;
- soft delete;
- reutilização de vínculo inativo;
- preservação da unicidade Produto × Material;
- correção do fluxo de PUT;
- validação integrada com o Front.

## 2. Escopo executado

A execução final ficou restrita aos seguintes arquivos:

- `src/main/java/br/com/inova/sigin/produtomaterial/repository/ProdutoMaterialRepository.java`
- `src/main/java/br/com/inova/sigin/produtomaterial/service/ProdutoMaterialService.java`

Não foram realizadas alterações em:

- Front;
- banco de dados;
- migrations;
- IAM;
- JWT;
- Produto;
- Material;
- outros módulos do Core.

## 3. Alterações realizadas

### 3.1 Reutilização de vínculo inativo

O fluxo de criação passou a localizar previamente o vínculo existente entre Produto e Material.

Quando o vínculo:

- não existe → um novo registro é criado;
- existe e está ativo → a operação é rejeitada por duplicidade;
- existe e está inativo → o registro existente é reativado.

Na reativação:

- `ativo` passa para `true`;
- `quantidade` recebe o novo valor;
- o mesmo registro é reutilizado;
- o ID existente é preservado.

Não é criada uma segunda linha para o mesmo Produto × Material.

### 3.2 Integridade

A constraint `uk_produto_material`, sobre `produto_id` e `material_id`, foi preservada.

Não foi criada migration.

### 3.3 Soft delete

O DELETE continua utilizando exclusão lógica:

`ativo = false`

O registro permanece persistido.

Não foi criado endpoint alternativo.

### 3.4 Transacionalidade

Os fluxos relevantes do `ProdutoMaterialService` passaram a permanecer dentro de transação.

Isso também resolveu a falha observada durante a montagem das respostas envolvendo relacionamentos `LAZY`, que provocava `LazyInitializationException`.

Não foi criada permission específica para ProdutoMaterial e não houve alteração conceitual no IAM.

## 4. Validação funcional

Foram validados manualmente:

- criação de novo vínculo;
- rejeição de duplicidade quando o vínculo está ativo;
- DELETE com soft delete;
- nova inclusão após DELETE;
- reutilização do vínculo inativo;
- preservação do ID durante a reativação;
- atualização da quantidade durante a reativação;
- alteração de quantidade via PUT;
- funcionamento integrado pelo Front.

## 5. Validação técnica

### Build

Build do Core concluído com sucesso.

### `git diff --check`

Executado com sucesso, sem erros.

### Testes automatizados

`mvn test` não foi executado nesta F08 porque a suíte existente encontra-se defasada em relação ao estado atual do Core.

Essa limitação deve ser tratada posteriormente como demanda própria de atualização da suíte.

## 6. Banco de dados

Nenhuma migration foi criada.

A constraint existente:

`uk_produto_material`

foi preservada.

## 7. Resultado

A F08 — Produto × Material foi concluída funcionalmente.

O comportamento final está alinhado às decisões consolidadas pelo Roadmap Core:

- DELETE representa soft delete;
- vínculo inativo pode ser reutilizado;
- vínculo ativo continua sendo duplicidade;
- ID do vínculo é preservado durante a reativação;
- quantidade é atualizada;
- unicidade Produto × Material permanece protegida.

## 8. Pendências

Permanecem fora da F08:

- política sistêmica de reativação;
- política geral de unicidade associada a soft delete;
- organização administrativa de registros inativos;
- organização futura dos menus administrativos;
- gerador automático de código de Material;
- atualização da suíte de testes automatizados.

## 9. Status

**CONCLUÍDA**
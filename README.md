# Documentação da API de Quiz
Esta documentação descreve os endpoints da API do sistema de Quiz.

## Modelos de Dados (Estruturas JSON)
Estas são as principais estruturas de dados (DTOs/Modelos) que a API envia e recebe.

### Jogador
Usado para criar e listar jogadores. Note que o `id` e o `time` são ignorados no JSON de resposta.

**JSON**
```json
{
  "nome": "Nome do Jogador",
  "especialidade": "Especialidade do Jogador"
}
```

### Time
Usado para criar e listar times. Note que o `id` é ignorado no JSON de resposta. A lista `jogadores` conterá objetos `Jogador`.

**JSON**
```json
{
  "nome": "Nome do Time",
  "pontos": 100.0,
  "dicas": 10,
  "jogadores": [
    {
      "nome": "Jogador 1",
      "especialidade": "Atacante"
    }
  ]
}
```

### Pergunta
Usado para criar e buscar perguntas. O `nivel` é serializado como uma string minúscula (ex: "facil").

**JSON**
```json
{
  "id": 1,
  "area_conhecimento": "História",
  "pergunta": "Quem descobriu o Brasil?",
  "imagem": {
    "id": 1,
    "url": "[http://example.com/imagem.png](http://example.com/imagem.png)",
    "base64Data": "data:image/jpeg;base64,iVBORw0..."
  },
  "alternativas": [
    "Opção 1",
    "Opção 2",
    "Opção 3",
    "Opção 4"
  ],
  "resposta": 0,
  "nivel": "facil"
}
```

### TimeDTO
Usado para enviar a pontuação no endpoint POST /quiz/acertou/{id}.

**JSON**

```JSON

{
  "pontos": 50.0,
  "dicasUsadas": 1
}
```


## 1. API de Jogadores (`/jogador`)
Controlador para gerenciar os jogadores.

### POST `/jogador/create`
Descrição: Cria um novo jogador.

Request Body: `Jogador`

**JSON**

```JSON

{
  "nome": "Novo Jogador",
  "especialidade": "Computação"
}
```

#### Respostas:

**200 OK:** Retorna o objeto `Jogador` criado.

**400 Bad Request:** Se um jogador com o mesmo ID já existir.

**500 Internal Server Error:** Caso exista dois jogadores com nomes iguais.


### GET `/jogador/{id}`**
Descrição: Busca um jogador específico pelo seu ID.

Parâmetro de Path: `id` (long) - O ID do jogador.

#### Respostas:

**200 OK:** Retorna o objeto Jogador encontrado.

**404 Not Found:** Se o jogador não for encontrado.

### PUT `/jogador/edit/{id}`
Descrição: Edita as informações de um jogador (nome e especialidade). Não altera o time.

Parâmetro de Path: `id` (long) - O ID do jogador a ser editado.

Request Body: Jogador (campos a atualizar)

**JSON**

```JSON

{
  "nome": "Jogador Editado",
  "especialidade": "Hídrica"
}
```

#### Respostas:

**200 OK:** Retorna o objeto `Jogador` atualizado.

**404 Not Found:** Se o `jogador` não for encontrado.

### DELETE `/jogador/remove/{id}`
Descrição: Remove um jogador do sistema.

Parâmetro de Path: `id` (long) - O ID do jogador a ser removido.

#### Respostas:

**204 No Content:** `Jogador` removido com sucesso (resposta sem corpo).

**404 Not Found:** Se o jogador não for encontrado.

## 2. API de Times (`/time`)
Controlador para gerenciar os times.

### POST `/time/create`
Descrição: Cria um novo time. O time é criado com um valor padrão de 10 dicas.

Request Body: `Time`

**JSON**

```JSON

{
  "nome": "Novo Time"
}
```
#### Respostas:

**200 OK:** Retorna o objeto `Time` criado (com `dicas: 10`).

**400 Bad Request:** Se um time com o mesmo ID já existir.

### GET `/time`
Descrição: Lista todos os times cadastrados.

#### Respostas:

**200 OK:** Retorna uma lista (`[]`) de objetos `Time`.

### GET `/time/{id}`
Descrição: Busca um time específico pelo seu ID.

Parâmetro de Path: `id` (long) - O ID do time.

#### Respostas:

**200 OK:** Retorna o objeto `Time` encontrado.

**404 Not Found:** Se o time não for encontrado.

### PUT /time/edit/{id}
Descrição: Edita as informações de um time (nome, pontos e lista de jogadores).

Parâmetro de Path: `id` (long) - O ID do time a ser editado.

Request Body: `Time` (campos a atualizar)

#### Respostas:

**200 OK:** Retorna o objeto `Time` atualizado.

**404 Not Found:** Se o time não for encontrado.

### DELETE /time/delete/{id}
Descrição: Remove um time do sistema.

Parâmetro de Path: `id` (long) - O ID do time a ser removido.

#### Respostas:

**204 No Content:** Time removido com sucesso.

**404 Not Found:** Se o time não for encontrado.

## 3. API de Associação Jogador/Time (`/jogador/time`)
Controlador para associar jogadores a times.

### POST `/jogador/time/add`
Descrição: Adiciona um jogador existente a um time existente.

Parâmetros de Query:

`jogadorId` (long): O ID do jogador.

`timeId` (long): O ID do time.

#### Respostas:

**200 OK:** Retorna o objeto `Jogador` atualizado (com a referência do time).

**404 Not Found:** Se o `jogadorId` ou o `timeId` não forem encontrados.


### DELETE `/jogador/time/remove`
Descrição: Remove a associação de um jogador com seu time (define `time = null`).

Parâmetros de Query:

`jogadorId` (long): O ID do jogador.

Respostas:

**200 OK:** Retorna o objeto Jogador atualizado (sem time).

**404 Not Found:** Se o jogadorId não for encontrado.

## 4. API de Perguntas (`/pergunta`)
Controlador para gerenciar o banco de perguntas. Importante: Endpoints que retornam `Pergunta` preenchem o campo base64Data da imagem.

### POST `/pergunta/create`
Descrição: Cria uma nova pergunta.

Request Body: `Pergunta`

#### Respostas:

**200 OK:** Retorna o objeto `Pergunta` criado (com dados Base64 da imagem, se houver).

### GET `/pergunta/{id}`
Descrição: Busca uma pergunta específica pelo seu ID.

Parâmetro de Path: `id` (long) - O ID da pergunta.

#### Respostas:

**200 OK:** Retorna o objeto `Pergunta` (com dados Base64).

**404 Not Found:** Se a pergunta não for encontrada.

### PUT `/pergunta/edit/{id}`
Descrição: Edita uma pergunta existente.

Parâmetro de Path: `id` (long) - O ID da pergunta.

Request Body: `Pergunta` (campos a atualizar)

#### Respostas:

**200 OK:** Retorna o objeto `Pergunta` atualizado (com dados Base64).

**404 Not Found:** Se a pergunta não for encontrada.

### DELETE `/pergunta/delete/{id}`
Descrição: Remove uma pergunta do sistema (e a imagem associada).

Parâmetro de Path: `id` (long) - O ID da pergunta.

#### Respostas:

**204 No Content:** Pergunta removida com sucesso.

**404 Not Found:** Se a pergunta não for encontrada.

### GET `/pergunta/random`
Descrição: Puxa uma pergunta aleatória do banco de dados e remove ela permanentemente.

#### Respostas:

**200 OK:** Retorna um objeto Pergunta aleatório (com dados Base64).

**404 Not Found:** Se não houver perguntas disponíveis.

## GET `/pergunta/dificil`
Descrição: Puxa uma pergunta de nível "DIFICIL" aleatória e remove ela permanentemente.

### Respostas:

**200 OK:** Retorna um objeto Pergunta "DIFICIL" (com dados Base64).

**404 Not Found:** Se não houver perguntas difíceis disponíveis.

## GET `/pergunta/medio`
Descrição: Puxa uma pergunta de nível "MEDIO" aleatória e remove ela permanentemente.

### Respostas:

**200 OK:** Retorna um objeto Pergunta "MEDIO" (com dados Base64).

**404 Not Found:** Se não houver perguntas médias disponíveis.

## GET `/pergunta/facil`
Descrição: Puxa uma pergunta de nível "FACIL" aleatória e remove ela permanentemente.

### Respostas:

**200 OK:** Retorna um objeto Pergunta "FACIL" (com dados Base64).

**404 Not Found:** Se não houver perguntas fáceis disponíveis.



















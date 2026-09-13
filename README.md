# Sistema de Gestão de Solicitações
_Desafio Técnico_

O Sistema de Gestão de Solicitações (SGS) é uma solução criada para apoiar o controle de solicitações de pagamento realizadas por diferentes áreas de uma organização. Ele foi proposto como desafio técnico para desenvolver uma aplicação web que permita o gerenciamento simples das solicitações de pagamento, possibilitando seu registro, consulta e acompanhamento, garantindo maior organização, rastreabilidade e controle do fluxo.

Autor(a): Géssica Kelly de Souza Santos

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- HTML
- CSS
- JavaScript

---

## Arquitetura

A arquitetura do projeto segue o requisito proposto, sendo separada em camadas com responsabilidades bem definidas.

- **Controller:** responsável por receber as requisições HTTP e disponibilizar os endpoints da API.
- **Service:** responsável pelas regras de negócio e pela coordenação das operações.
- **Repository:** responsável pelo acesso e consulta aos dados.
- **DTO:** responsável pela transferência dos dados de entrada e saída da API.
- **Entity:** representa as entidades persistidas no banco de dados.
- **Exception:** responsável pelo tratamento das exceções da aplicação e pelo retorno adequado dos erros da API.

O frontend é desenvolvido com HTML, CSS e JavaScript puro e é servido pela própria aplicação Spring Boot.

---

## Estrutura do projeto

```text
sgs/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── sgs/
│       │           ├── controller/
│       │           ├── dto/
│       │           ├── entity/
│       │           ├── exception/
│       │           ├── repository/
│       │           ├── service/
│       │           └── SgsApplication.java
│       │
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   │   ├── reset.css
│           │   │   └── style.css
│           │   ├── js/
│           │   │   ├── cadastro.js
│           │   │   ├── detalhes.js
│           │   │   └── listagem.js
│           │   ├── cadastro.html
│           │   ├── detalhes.html
│           │   └── index.html
│           │
│           └── application.yaml
│
├── database/
│   └── sql/
│       ├── ddl.sql
│       └── dml.sql
│
├── pom.xml
└── README.md
```

Os scripts necessários para criação e carga inicial do banco estão disponíveis em:

- `ddl.sql`: responsável pela criação das tabelas, relacionamentos e restrições.
- `dml.sql`: responsável pela inserção dos dados iniciais utilizados pela aplicação.

---

## Funcionalidades

O SGS disponibiliza as seguintes funcionalidades:

- Cadastro de novas solicitações de pagamento.
- Consulta das solicitações cadastradas.
- Filtro das solicitações por status, período e categoria.
- Visualização dos dados completos de uma solicitação.
- Atualização do status diretamente pela listagem.
- Validação das transições de status conforme as regras de negócio.

---

## Como executar

### Pré-requisitos

Para executar o projeto, é necessário possuir:

- Java 21
- PostgreSQL
- Maven

### 1. Clone o repositório

```bash
git clone https://github.com/gehly00/sgs.git
```

Acesse a pasta do projeto:

```bash
cd sgs
```

### 2. Prepare o banco de dados

Primeiro, crie o banco de dados:

```sql
CREATE DATABASE sgs;
```

Em seguida, conecte-se ao banco `sgs` e execute os scripts na seguinte ordem:

```text
1. database/sql/ddl.sql
2. database/sql/dml.sql
```

### 3. Configure a conexão

A conexão com o banco de dados é configurada por meio de variáveis de ambiente.

No arquivo `application.yaml`, a aplicação utiliza as seguintes variáveis:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

Antes de iniciar a aplicação, configure as seguintes variáveis de acordo com o seu ambiente:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Exemplo de URL para uma instalação local do PostgreSQL:

```text
jdbc:postgresql://localhost:5432/sgs
```

### 4. Execute a aplicação

Execute a aplicação utilizando o Maven.
```text
mvn spring-boot:run
```

### 5. Acesse a aplicação

Com a aplicação em execução, acesse no navegador:

```text
http://localhost:8081
```

---

## Fluxo de status

Toda nova solicitação é criada automaticamente com o status `SOLICITADO`.

As alterações de status seguem as regras de negócio abaixo:

```text
SOLICITADO
├── LIBERADO
│   ├── APROVADO
│   │   └── CANCELADO
│   └── REJEITADO
└── REJEITADO
```

As transições permitidas são:

| Status atual | Próximo status permitido |
|---|---|
| `SOLICITADO` | `LIBERADO` ou `REJEITADO` |
| `LIBERADO` | `APROVADO` ou `REJEITADO` |
| `APROVADO` | `CANCELADO` |
| `REJEITADO` | Nenhuma transição |
| `CANCELADO` | Nenhuma transição |

Os status `REJEITADO` e `CANCELADO` são finais e não permitem novas alterações.

As regras de transição são validadas no backend, garantindo que uma solicitação não possa assumir um status incompatível com o fluxo definido.

---
## Endpoints da API

A aplicação disponibiliza os seguintes endpoints:

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/solicitantes` | Lista os solicitantes disponíveis |
| `GET` | `/api/categorias` | Lista as categorias disponíveis |
| `GET` | `/api/solicitacoes` | Lista as solicitações e permite aplicação de filtros |
| `GET` | `/api/solicitacoes/{id}` | Consulta os detalhes de uma solicitação |
| `POST` | `/api/solicitacoes` | Cadastra uma nova solicitação |
| `PATCH` | `/api/solicitacoes/{id}/status` | Atualiza o status de uma solicitação |

### Filtros da consulta

O endpoint de listagem aceita os seguintes parâmetros opcionais:

| Parâmetro | Descrição |
|---|---|
| `status` | Filtra pelo status da solicitação |
| `dataInicio` | Define a data inicial do período |
| `dataFim` | Define a data final do período |
| `categoriaId` | Filtra pela categoria |

Exemplo:

```text
GET /api/solicitacoes?status=SOLICITADO&categoriaId=1
```

---

## Decisões técnicas

O projeto foi desenvolvido buscando manter a implementação simples e adequada ao escopo proposto.

### Backend

- O backend foi desenvolvido com Spring Boot e organizado em camadas, mantendo separadas as responsabilidades de acesso aos dados, regras de negócio e exposição da API.

- As regras de transição de status são validadas no backend, garantindo que não dependam do comportamento do frontend.

### Persistência

- O Spring Data JPA é utilizado nas operações comuns de persistência.

- A listagem principal de solicitações foi implementada utilizando SQL nativo, conforme requisito do desafio. A consulta realiza `JOIN` entre as tabelas `solicitacao`, `solicitante` e `categoria` e permite a aplicação dinâmica de filtros por status, período e categoria.

- Os valores dos filtros são enviados à consulta por meio de parâmetros nomeados.


### Frontend

- Os arquivos são disponibilizados como recursos estáticos pela própria aplicação Spring Boot, evitando a necessidade de executar ou configurar uma aplicação frontend separada.

- O JavaScript utiliza a Fetch API para consumir os endpoints disponibilizados pelo backend.

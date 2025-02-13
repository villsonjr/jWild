# ulk-aniamlz

**Ulk-aniamlz** é uma API RESTful desenvolvida com Spring Boot para registro e gerenciamento de registros de animais.
Este projeto utiliza tecnologias modernas e práticas de desenvolvimento ágil para proporcionar uma solução eficiente e
escalável.

## Visão Geral do Projeto

- **Descrição**: Esta API permite o registro, consulta, atualização e remoção de registros de animais. Foi desenvolvida
  utilizando o framework Spring Boot, promovendo a facilidade de escalabilidade e integração com bancos de dados como H2
  e MySQL.

## Tecnologias Utilizadas

- **Spring Boot**: O núcleo da aplicação fornece o suporte para RESTful API, validação, JPA e muito mais.
- **Spring Boot Starter Actuator**: Utilizado para monitoramento e gerenciamento da aplicação.
- **Spring Boot Starter Data JPA**: Permite o uso eficiente de JPA para interação com o banco de dados.
- **Spring Boot Starter HATEOAS**: Facilita a construção de APIs RESTful com ligações entre recursos.
- **Spring Boot Starter Validation**: Implementa validações nos dados enviados e recebidos.
- **Spring Boot Starter Web**: Fornece suporte a web para construir a interface RESTful.
- **Spring Boot Starter Security**: Suporte a autenticação e segurança na aplicação.
- **JSON Web Tokens (JWT)**: Implementação de tokens JWT para autenticação e autorização.
- **H2 Database**: Um banco de dados em memória leve utilizado para desenvolvimento.
- **Flyway**: Ferramenta para gerenciamento de migrações de banco de dados
- **MySQL** : Banco de dados relacional
- **MySQL Connector**: Conector para se conectar ao banco de dados MySQL em tempo de execução.
- **Lombok**: Para reduzir o boilerplate code e melhorar a legibilidade do código.
- **OpenAPI Swagger**: Ferramenta para geração de documentação automática da API.
- **Mockito**: Utilizado para testes unitários.
- **Spring Boot Starter Test**: Fornece os pacotes necessários para os testes da aplicação.
- **Docker**: Contêinerização da aplicação

## Configurações de Ambiente

- **Java Version**: 21
- **IDE**: Eclipse, IntelliJ ou outro suporte ao Spring Boot

## Pré-requisitos
- **Docker**: Você precisa ter o [Docker](https://www.docker.com/get-started) instalado em sua máquina para rodar a aplicação e o banco de dados em contêineres.
- **Docker Compose**: Necessário para orquestrar múltiplos contêineres de uma vez. O [Docker Compose](https://docs.docker.com/compose/install/) é usado para definir os serviços no ambiente Docker.

## Dependências do Projeto

O `pom.xml` contém todas as dependências necessárias para o funcionamento da aplicação, incluindo as dependências para
validação, banco de dados, documentação da API e testes.

## Como Executar

1. Clone o repositório do projeto.
2. Execute a aplicação utilizando o docker-compose:

```console
   docker-compose up --build
```

## Documentação

A documentação da API pode ser acessada através do Swagger: `http://localhost:8080/swagger-ui.html`

## Testes

Os testes unitários podem ser executados utilizando o Maven:

```console
   mvn test
```
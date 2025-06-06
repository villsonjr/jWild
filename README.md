# jWild

**jWild** é uma API RESTful desenvolvida com Spring Boot para registro e gerenciamento de registros de animais.
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
- **H2 Database**: Um banco de dados em memória leve utilizado para desenvolvimento.
- **MySQL Connector**: Conector para se conectar ao banco de dados MySQL em tempo de execução.
- **Lombok**: Para reduzir o boilerplate code e melhorar a legibilidade do código.
- **OpenAPI Swagger**: Ferramenta para geração de documentação automática da API.
- **Mockito**: Utilizado para testes unitários.
- **Spring Boot Starter Test**: Fornece os pacotes necessários para os testes da aplicação.

## Configurações de Ambiente

- **Java Version**: 21
- **IDE**: Eclipse, IntelliJ ou outro suporte ao Spring Boot

## Dependências do Projeto

O `pom.xml` contém todas as dependências necessárias para o funcionamento da aplicação, incluindo as dependências para
validação, banco de dados, documentação da API e testes.

## Como Executar

1. Certifique-se de ter o Java 21 instalado.
2. Clone o repositório do projeto.
3. Configure o banco de dados H2/MySQL conforme necessário.
4. Execute a aplicação utilizando o Maven:

```console
   mvn spring-boot:run
```

## Documentação

A documentação da API pode ser acessada através do Swagger: `http://localhost:8080/swagger-ui.html`

## Testes

Os testes unitários podem ser executados utilizando o Maven:

```console
   mvn test
```

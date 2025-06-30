# Challenger

## Tecnologias Utilizadas

- **Java 21**  
  Versão estável mais atual do Java.

- **Spring Boot 3.5.3**  
  Framework para facilitar a criação de aplicações Java.

- **Maven**  
  Gerenciador de dependências e build.

- **PostgreSQL**  
  Banco de dados relacional utilizado.

- **Flyway**  
  Controle de versionamento e migração do banco de dados.

- **Testcontainers**  
  Utilizado para testes de integração com banco de dados real em containers.

- **JaCoCo**  
  Ferramenta para análise de cobertura de testes.

- **Docker Compose**  
  Dockerização da API e do Banco rodando através do docker.

## Padrões Arquiteturais

- **Camadas (Layered Architecture)**  
  Separação em camadas: Controller, Service, Repository, promovendo organização e desacoplamento.

- **DTOs (Data Transfer Objects)**  
  Utilizados para transferência de dados entre camadas e exposição de APIs.

- **Migrations**  
  Gerenciamento de scripts de banco via Flyway, garantindo versionamento e reprodutibilidade.

## Estrutura dos Testes

- **Testes Unitários**  
  Localizados em `src/test/java`, executados com o Maven Surefire.

- **Testes de Integração**  
  Localizados em `src/integration-test/java`, executados com Maven Failsafe e Testcontainers.

## Como Executar

- `make start` — Inicia a aplicação rodando tudo através do docker.
- `make build` — Gera o pacote da aplicação sem rodar os testes.
- `make build-image` — Gera a imagem Docker da aplicação.
- `make docker-up` — Sobe os containers Docker (após build da imagem).
- `make test-unit` — Executa testes unitários.
- `make test-integration` — Executa testes de integração.
- `make test-coverage` — Gera relatório de cobertura de testes.

## Documentação da API

Acesse a documentação interativa do Swagger em:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
ou  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Observações

- O projeto segue boas práticas de modularização, versionamento de banco e cobertura de testes.
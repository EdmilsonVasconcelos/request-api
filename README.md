# Back-end do projeto de pedidos

### Tecnologias

- Java 11
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Docker

# Executando a aplicação local

- Vá até a raíz do projeto e use o comando `make run-db` (você precisará do Docker). Após isso, o container do `PostgreSQL` será iniciado. A partir deste momento você poderá rodar a aplicação na sua `IDE` de preferência. Ao iniciar a aplicação pela primeira vez, o banco de dados `request` e todas as tabelas serão criadas automaticamente. Os endpoints estão rodando na `http://localhost:8080`.

# Acessando a área de administrador

- Crie um usuário no endpoint `http://localhost:8080/v1/admin` usando o verbo `POST`.
```json
{
  "name": "teste",
  "email": "teste@email.com",
  "password": "suasenha"
}
```
- Acesse http://localhost/request/admin/ no sistema e logue com seu usuário (***Você precisará do front-end configurado***).

# Configurando um gerenciador de banco de dados

- Escolha um gerenciador de banco de dados de sua preferência. Alguns exemplos: `DBeaver`, `MySQL Workbeanch`.
- Crie uma nova conexão
  - usuário: postgres
  - senha: postgres
  - banco de dados: request
  - porta: 5432

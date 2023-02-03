# Back-end do projeto de pedidos

### Tecnologias

- Java 11
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Docker

# Executando a aplicação local

- Vá até a raíz do projeto e use o comando `make run-db` (você precisará do Docker). Após isso o container do `MySQL` será iniciado. A partir deste momento você pode rodar a aplicação. Ao iniciar a aplicação pela primeira vez, o banco de dados `request` e todas as tabelas serão criadas automaticamente. Os endpoints estão rodando na `http://localhost:8080`.

# Acessando a área de administrador

- Crie um usuário usando a API: http://localhost:8080/v1/admin passando o `name`, `email` e `password`. 
- Acesse http://localhost/request/admin/ no sistema e logue com seu usuário.

# Configurando um gerenciador de banco de dados

- Escolha um gerenciador de bd de sua preferência. Alguns exemplos: `DBeaver`, `MySQL Workbeanch`.
- Crie uma nova conexão
  - usuário: root
  - senha: espaço em branco
  - banco de dados: request
  - porta: 306

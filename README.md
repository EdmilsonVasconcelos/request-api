# Back-end do projeto de pedidos

### Tecnologias

- Java 11
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Docker

# Iniciando o projeto

- Com o docker iniciado, vá até a raíz do projeto e use o comando `make run-app`. Depois disso você terá o container do `app` iniciado e todos os endpoints estarão disponíveis. Ao iniciar o projeto pela primeira vez, o banco de dados chamado `request` e todas as tabelas serão criados automaticamente. O projeto roda na `http://localhost:8080`.

# Para acessar a área de administrador

- Crie um usuário usando a API: http://localhost:8080/v1/admin passando o `name`, `email` e `password`. 
- Acesse http://localhost/request/admin/ no sistema e logue com seu usuário.

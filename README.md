# request-api

Back-end do projeto de pedidos

### Tecnologias

- Spring boot
- Spring Data JPA
- Spring Security
- MySQL

# Para configurar o projeto:

- Você precisa ter configurado o Xamp, nele você inicia o MySQL.
- Após isso, é necessário criar um usuário no banco de dados como administrador.
    - Com o MySQL do Xamp iniciado, vá até a tabela request.
    - Você precisará de uma senha criptografada usando o BCryptPasswordEncoder para criar seu usuário direto no banco de dados e logar como administrador. Uma dica é criar um método main e gerar a senha desejada. Ex: new BCryptPasswordEncoder().encode("123123").
    - Vá até a tabela user e adicione seu usuário 
- Ao rodar o projeto pela primeira vez, o banco de dados e as tabelas irão ser criadas automaticamente
- Acessar a url: http://localhost/request/admin


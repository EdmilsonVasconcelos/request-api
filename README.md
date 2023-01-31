# request-api

Back-end do projeto de pedidos

### Tecnologias

- Spring boot
- Spring Data JPA
- Spring Security
- MySQL

# Para configurar o projeto:

- Você precisa ter configurado o Xamp.
- Inicie o MySQL do Xamp.
- Após isso, é necessário criar um usuário no banco de dados como administrador.
    - Vá até a tabela admin.
    - Você precisará de uma senha criptografada usando o BCryptPasswordEncoder para criar seu usuário direto no banco de dados e logar como administrador. Uma dica é usar o método main de inicialização do projeto, imprimindo a senha desejada. Ex: System.out.println(new BCryptPasswordEncoder().encode("123123")).
    - Vá até a tabela admin e adicione seu usuário 
- Ao rodar o projeto pela primeira vez, o banco de dados e as tabelas serão criadas automaticamente.
- Acessar a url: http://localhost/request/admin


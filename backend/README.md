# MyCash

## Back-End Sistema de controle pessoal de despesas.

Projeto desenvolvido durante o Bootcamp Desenvolvimento Full Stack Java Spring Boot.

### Camadas da aplicação MyCash

<img src="https://user-images.githubusercontent.com/35788149/121291773-39dc9c00-c8bf-11eb-8f89-49b217d9b400.jpg" width="400px" alt=""/>

1)	Criaremos os pacotes web.api, service, domain, repository: [Git](https://git-scm.com)

2)	Dentro de domain, criar a classe Lancamento e enum LancamentoTipo:

3)	Dentro de web.api, criaremos a classe LancamentoController:

4)	Configuramos arquivo application.properties:
    server.port=8080
    server.servlet.context-path=/mycash

5)	Testar com Postman.

    a)	GET: http://localhost:8080/mycash/api/lancamento 
    b)	POST: http://localhost:8080/mycash/api/lancamento 
    c)	PUT: http://localhost:8080/mycash/api/lancamento/1 
    d)	DELETE: http://localhost:8080/mycash/api/lancamento/1

6)	Anotamos a classe Lancamento para que se torne uma Entidade JPA.

7)	Configuramos o banco de dados em memória H-2 no arquivo application.properties:

8)	Acessemos o console do banco H2 e valide as tabelas criadas: http://localhost:8080/mycash/h2-console 

9)	Crieamos a Interface LancamentoRepository.

10)	Na Entidade Lancamento, adicionamos as validações:

    a)	NotBlank para descrição
    b)	NotNull para Data
    c)	NotNull para valor
    d)	Min 0 para valor
    e)	NotNull para tipo
    
11)	Crieamos a classe CustomGlobalExceptionHandler para manipular as mensagens de erro.


12)	Criamos as classes Serviços:

    a)	Classe LancamentoService. 
    a)	Injetamos LancamentoRepository na classe LancamentoService
    
13)	 Criamos a classe LançamentoController

14)	Delegamos a implementação dos métodos da classe LancamentoController para LancamentoService;


### Fizemos a segurança da aplicação com Spring Security

Com Spring Security trabalhamos com recursos avançados e de simples configuração para lhe ajudar com a segurança da aplicação.

    a) Configuramos autenticação em memória
    b) Criamos permissões (autorização) em nossas aplicação

## Deploy Spring Boot no Heroku

1.	Definimos a versão do java 11:

    a.	Criar arquivo system.properties na raíz do projeto com:

        java.runtime.version=11

```bash
$ git clone <https://github.com/LivioNeiva/Mycash-Gastos.git>

# Deploy Spring Boot no Heroku
# Execute a aplicação
# Acesse no navegador ou Postman <http://localhost:8080>
```

Referências:

    https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
    https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java
    https://devcenter.heroku.com/articles/java-support#specifying-a-java-version 








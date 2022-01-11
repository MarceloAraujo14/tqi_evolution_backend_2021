Este projeto foi elaborado como uma solução para o desafio proposto a todos que concluíram o Bootcamp TQI na plataforma da Digital Innovation One e promoveu um grande crescimento a nível de conhecimento em mim e nos outros participantes do Bootcamp. Experimentar uma pequena demanda do mercado real mostra como ainda temos espaço para evoluir e a vontade de finalizar o projeto como planejado foi maior do que a vontade pela entrega. Fomos movidos pelo desafio. 

---
**O desafio:**
Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades.
**i. Cadastro de clientes**
 O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.
**ii. Login**
  A autenticação será realizada por e-mail e senha.
**iii. Solicitação de empréstimo**
    Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.
    O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.
**iv. Acompanhamento das solicitações de empréstimo**
 O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
    Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
    No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

---
   **Planejamento**
    A ideia inicial para resolver o desafio proposto foi utilizar a arquitetura de microsserviços baseada nos conhecimentos adquiridos através do bootcamp, possibilitando o máximo desacoplamento entre os serviços oferecidos pela aplicação. Assim ficou definido que dois microsserviços seriam necessários:
  **UserApp:**
  Permite o acesso aos end-points de cadastro e Cliente através de uma interface web.
  **Cadastro:**
  Permite o acesso aos end-points de cadastro do possível cliente no serviço oferecido pela empresa. 
  Responsável por validar os dados inseridos pelo usuário, retornar os erros encontrados na submissão e cadastrar o usuário validado no banco de dados do servidor. 
   **Cliente:**
  Permite o acesso aos end-points de manipulação da entidade cliente como solicitar um empréstimo, retornar a lista de empréstimos referente ao cliente solicitante, visualizar os próprios dados cadastrados, assim como atualizar alguns dados não sensíveis (como documentos ou o email de acesso). 
  Responsável por assegurar que as informações solicitadas sejam retornadas apenas a um usuário logado com o papel de CLIENTE atrelado a ele, validar o empréstimo solicitado retornando os erros encontrados na submissão de solicitação e cadastrar o empréstimo atrelado as informações do usuário solicitante no banco de dados do servidor.
 
---
**Implementação:**
A ideia desde o começo foi montar a aplicação na arquitetura de microsserviço, para isso escolhi o framework Spring Boot que com todas as suas ferramentas facilitam o desenvolvimento de aplicações web, permitindo através da ferramenta Initalizer (https://start.spring.io) pré-configurar a aplicação com todas as dependências necessárias e com poucas linhas de código já ter uma api disponível.
Seguindo essa ideia, montei a aplicação de cadastro e cliente como uma só para facilitar o desenvolvimento.
Criando a entidade cliente, configurando a interface repositório, os controllers para export os end-points e a conexão com o banco de dados Elasticsearch. Que é um banco NoSQL orientado a documentos indexados em memória, o que trás um desempenho incrível em buscas.
O banco foi gerado através de um container Docker com a imagem disponível em https://www.elastic.co/guide/en/elasticsearch/reference/7.16/docker.html onde estão as instruções para baixar e rodar a imagem diretamente do container na porta 9200.

---
**Comunicação Cloud:**

A comunicação entre os microsserviços foi feita utilizando o Config-Client na aplicação, que permite que os serviços se conectem ao Config-Server para buscar a configuração necessária que foi disponibilizada no repositório online git: 
```
https://github.com/MarceloAraujo14/cliente-config.git 
```
O Eureka da Netflix que por sua vez, permite que os microsserviços se "registrem" com seu endereço e possam ser encontrados pelo Gateway (nossa conexão com serviços externos) que funciona como um "portão" para as requisições, recebendo-as e buscando no Eureka o caminho do serviço especificado e devolvendo ao cliente que solicitou. 

---
Vídeo demonstrando o funcionamento da interface da API
https://youtu.be/uDQ6VBCfV34

---
  **End-points:**
  
**UserApp:** 
	- Acesso a Home: 
	```
	- GET - 8080/
	```

**Cadastro:**
   - Interface: 
	 - Formulário de Cadastro: 
	 ``` 
	 - GET - localhost:8080/cadastro/novo
	 ```
	 - Submeter Formulário de Cadastro: 
	 ```
	 - POST - localhost:8080/cadastro/novo
	 ```

	
   - Postman:
	  - Enviar Dados no Body: 
	  ```
	  - POST - localhost:8080/cadastro/cadastrar
	  ```
	  - Formato J-son: 
	  
```
{ "nome":  "Anakin Skywalker",
"email":  "darth_vader@gmail.com",
"cpf":  "10267604025",
"rg":  "405738109",
"renda":  15000,
"senha":  "D34Th-5T4R",
"enderecos":  
[{
"endereco":  "Estrela da Morte",
"numero":  "42",
"complemento":  "Terceiro quadrante",
"bairro":  "Galaxia",
"cidade":  "Distante",
"estado":  "RJ",
"cep":  "23442-442",
"tipo":  "TRABALHO"
}]
}  
```
  
 **Cliente:**
   - Interface: 
	 - Home do Cliente: 
	 ```
	 - GET - localhost:8080/clientes/home 
	 ```
	 - Mostrar os Dados do Cliente Logado: 
	 ```
	 - GET - localhost:8080/clientes/dados
	 ```
	 - Atualizar Dados do Cliente Logado: 
	 ```
	 - GET - localhost:8080/clientes/atualizar
	 ```
	 - Formulário de Contrato de empréstimo: 
    
     ```
	 - GET - localhost:8080/clientes/contratar
	 ```
   
	 - Listar Empréstimos Contratados: 
	 ```
	 - GET - localhost:8080/clientes/emprestimos
	 ```
	 - Acessar detalhe de um Empréstimo: 
	 ```
	 - GET - localhost:8080/clientes/detalhes/{código do empréstimo}
	 ```
	- Postman: 
	  - Retornar dados do Cliente Logado: 
	  ```
	  - POST - localhost:8080/clientes/{email}
	  ```
	  observação: a api só ira retornar caso o email enviado seja do cliente logado. 
	   - Atualizar Dados do Cliente Logado: 
	   ```
	   - POST - localhost:8080/clientes/{email do cliente}
	   ```
	   - Solicitar Empréstimo: 
	   ```
	   - POST - localhost:8080/clientes//emprestimo/contratar/{email}
	   ```
	   - Listar Empréstimos Contratados: 
	   ```
	   - GET - localhost:8080/clientes/emprestimos/{email}
	   ```
	   - Acessar detalhe de um Empréstimo: 
	   ```
	   - GET - localhost:8080/emprestimos/detalhes/{codigo}{email}
	   ```
	 

---

**Ferramentas utilizadas:** 
**Backend**
 - Java 17
 - Maven
 - Spring Initalizer
 - Spring Boot Web
 - Spring Security
 - Spring Cloud (Config-Server, Gateway, Eureka)
 - Spring Data (Elastic-Search)
 
 **Front-End**
 - Spring Template Engine(Thymeleaf)
 - HTML
 - Bootstrap

**Banco de Dados**
- Elasticsearch (imagem)
- Docker

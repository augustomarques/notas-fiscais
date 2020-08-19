# APP Notas Fiscais

API para cadastro de Empresas e Notas Fiscais.


## Executando a aplicação
O projeto pode ser executado de duas maneiras.


####  Docker Compose

A maneira mais simplês é através do DockerCompose. Desta forma será iniciando também um banco de dados PostgreSQL. Basta executar o comando abaixo na raiz do projeto:

    docker-compose up

#### Maven/JAR
A segunda forma é compilando a aplicação através do Maven e executando diretamente o arquivo .jar. Desta forma o sistema irá utilizar o banco de dados H2. Na raiz do projeto execute os seguinte comandos: 

    mvn clean install 
    java -jar ./target/notasfiscais-0.0.1-SNAPSHOT.jar

## Interagindo com a API
O sistema esta configurado para atender as requisições na porta 8080. Todas as rotas estão disponiveis no Swagger:

`http://localhost:8080/swagger-ui.html  `

## Stack:

    Java (11)
    SpringBoot (2.3.3)
    Docker (19.03.12)
    Docker-Compose (1.26.2)
	PostgreSQL (12.3)

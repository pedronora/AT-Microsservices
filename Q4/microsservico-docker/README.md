# Microsserviço Spring Boot com Docker

Este projeto é um exemplo de microsserviço Spring Boot configurado para rodar em um contêiner Docker. O serviço expõe a porta 8080 para comunicação externa.

## Pré-requisitos

Antes de começar, você precisará das seguintes ferramentas instaladas em sua máquina:

- [Docker](https://www.docker.com/get-started)
- [Maven](https://maven.apache.org/)

## Estrutura do Projeto

A seguir está uma visão geral das etapas para construir e rodar o projeto:

1. **Criar o JAR do projeto:**
   O Maven é utilizado para gerar o JAR da aplicação.

2. **Construir a imagem Docker:**
   O Dockerfile é usado para construir a imagem da aplicação Spring Boot.

3. **Executar o contêiner:**
   A imagem Docker criada será usada para iniciar um contêiner, expondo a porta correta para acesso ao serviço.

## Instruções de Execução

### 1. Clonar o Repositório

Primeiro, clone o repositório para sua máquina local:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Compilar o Projeto
   Execute o Maven para compilar o projeto e gerar o arquivo JAR:

```bash
mvn clean package
```
Esse comando criará o arquivo JAR na pasta target/.

#### 3. Construir a Imagem Docker
   Utilize o Docker para construir a imagem da aplicação. Certifique-se de estar no diretório raiz do projeto onde o Dockerfile está localizado.

```bash
docker build -t nome-da-imagem .
```

Aqui, o . se refere ao diretório atual. Ele utiliza o Dockerfile para construir a imagem com o nome especificado.

#### 4. Executar o Contêiner
   Com a imagem Docker criada, agora você pode rodar o contêiner com o seguinte comando:

```bash
docker run -d -p 8080:8080 --name nome-do-container nome-da-imagem
```
Isso irá:
- Rodar o contêiner em segundo plano (-d).
- Mapear a porta 8080 do contêiner para a porta 8080 do host (-p 8080:8080).
- Definir um nome para o contêiner (--name nome-do-container).

### 5. Acessar o Serviço
   Depois que o contêiner estiver rodando, você poderá acessar o microsserviço via http://localhost:8080.

### 6. Parar o Contêiner
   Para parar o contêiner, utilize o comando:

```bash
docker stop nome-do-container
```

### 7. Remover o Contêiner
   Se você quiser remover o contêiner após parar a execução:

```bash
docker rm nome-do-container
```

## Personalização
- Porta: Se você quiser expor o serviço em outra porta, basta alterar a opção -p no comando docker run (por exemplo, -p 9090:8080).
- Imagem: Você pode personalizar o nome da imagem alterando a opção -t no comando docker build.

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença
Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).


### Explicação sobre conceitos funcionais no código do projeto

Embora o exemplo acima não tenha diretamente a ver com programação funcional, podemos adotar alguns princípios de **funções puras** e **imutabilidade** na estrutura do nosso código Spring Boot:

- **Funções puras**: Todas as funções que realizam transformações de dados no microsserviço podem ser puras, ou seja, elas sempre produzem o mesmo resultado para os mesmos parâmetros de entrada, sem efeitos colaterais.

- **Imutabilidade**: Para garantir a imutabilidade dos dados, sempre que manipularmos entidades ou objetos no microsserviço, podemos retornar novas instâncias ao invés de modificar os objetos existentes. Isso facilita o rastreamento de estados, evitando bugs relacionados a mudanças imprevisíveis no estado da aplicação.

Esses princípios ajudam a criar um código mais previsível e fácil de testar, integrando boas práticas de programação funcional no desenvolvimento com Spring Boot.

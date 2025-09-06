# Cafts - Payment Processing Service

Este é um serviço de processamento de pagamentos desenvolvido em Java com Spring Boot. O serviço é projetado para processar diferentes tipos de pagamento, como Cartão de Crédito e PIX, de forma assíncrona usando RabbitMQ.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
  - Spring Web
  - Spring Data JPA
  - Spring AMQP (RabbitMQ)
- **PostgreSQL**: Banco de dados relacional para persistir os dados dos pagamentos.
- **RabbitMQ**: Message broker para processamento assíncrono de pagamentos.
- **Lombok**: Para reduzir código boilerplate em entidades e DTOs.
- **Maven**: Gerenciador de dependências e build.
- **Docker & Docker Compose**: Para containerização e orquestração do ambiente de desenvolvimento.

## Pré-requisitos

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Como Executar o Projeto

O projeto é totalmente containerizado, facilitando a execução em qualquer ambiente com Docker.

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd cafts
   ```

2. **Execute o Docker Compose:**
   Na raiz do projeto, execute o seguinte comando para construir as imagens e iniciar os contêineres:
   ```bash
   docker-compose up --build
   ```
   - O comando `up` inicia todos os serviços definidos no `docker-compose.yml`.
   - A flag `--build` garante que a imagem da aplicação seja reconstruída com as últimas alterações do código.

3. **Acesse os serviços:**
   - **API do Serviço**: `http://localhost:8080`
   - **RabbitMQ Management UI**: `http://localhost:15672` (usuário: `guest`, senha: `guest`)
   - **Banco de Dados PostgreSQL**: Acessível na porta `5432` (não exposto publicamente por padrão, apenas para a aplicação).

## Documentação da API

### Processar um Pagamento

Envia uma requisição para processar um novo pagamento. O tipo de pagamento é determinado pelo campo `paymentType`.

- **Endpoint**: `POST /payment`
- **Content-Type**: `application/json`

#### Exemplo de Requisição - Cartão de Crédito

```json
{
  "amount": 250.75,
  "paymentType": "CREDIT_CARD",
  "userId": "user-abc-123",
  "creditCard": {
    "cardNumber": "4242424242424242",
    "cardHolder": "Lucas Hernandez",
    "cardExpiration": "2027-10-10",
    "cardCvv": "123",
    "installments": 1
  }
}
```

#### Exemplo de Requisição - PIX

```json
{
  "amount": 150.00,
  "paymentType": "PIX",
  "userId": "user-def-456",
  "pix": {
    "pixKey": "a2c3e4b5-a2d7-4a6c-9c8a-3d7f4e1b9a0c",
    "pixKeyType": "RANDOM"
  }
}
```

#### Respostas

- **`200 OK`**: Se a requisição de pagamento for recebida com sucesso para processamento.
- **`400 Bad Request`**: Se houver um erro de validação nos dados enviados (ex: número de cartão inválido, chave PIX inválida, campos faltando).

## Arquitetura e Padrão Strategy

O serviço é projetado em torno de um padrão **Produtor-Consumidor** com processamento assíncrono, utilizando o **Padrão de Projeto Strategy** no lado do consumidor para máxima flexibilidade.

### Fluxo da Requisição

1.  **Produtor (`PaymentService`):**
    -   O endpoint `POST /payment` recebe a requisição.
    -   O `PaymentService` realiza a validação dos dados e cria as entidades (`Payment`, `PaymentCreditCard`/`PaymentPix`) com o status inicial `PROCESSING`.
    -   Os dados são salvos no banco de dados.
    -   O **ID** do pagamento é publicado em uma fila do RabbitMQ (`payment.process`). O serviço não espera pelo resultado do processamento.

2.  **Consumidor (`PaymentConsumer`):**
    -   O `PaymentConsumer` escuta a fila `payment.process`.
    -   Ao receber um ID, ele busca a entidade `Payment` completa no banco de dados.
    -   Ele utiliza uma `PaymentStrategyFactory` para obter a estratégia de processamento correta (`CreditCardPayment` ou `PixPayment`) com base no `paymentType` do pagamento.
    -   A estratégia apropriada é executada para processar o pagamento (neste exemplo, ela apenas atualiza o status para `APPROVED`, mas em um cenário real, ela se comunicaria com um gateway de pagamento).

Este design desacopla a requisição inicial do processamento final, tornando a API mais rápida e o sistema mais resiliente e escalável.

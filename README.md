## Como rodar o projeto localmente

### Pré-requisitos
- Java 21
- Docker
- Docker Compose

### Passos para rodar

1. Clone este repositório.
2. Navegue até o diretório do projeto através de um terminal.
3. Suba o ambiente com o comando:

   ```bash
   docker-compose up

4. Abra outro terminal.
5. Crie as filas SQS com o comando:

    ```bash
    aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name partial-payment
    aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name total-payment
    aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name excess-payment
   
6. Suba o servidor na classe DesafioSanGiorgioApplication.java
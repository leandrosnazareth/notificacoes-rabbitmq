# Notificações RabbitMQ

Este projeto implementa um sistema de notificações em Java (Spring Boot) que gerencia eficientemente o tempo de espera por respostas de usuários, utilizando uma arquitetura orientada a eventos com RabbitMQ.

## Motivação

O sistema foi criado para substituir o método tradicional de polling (consultas repetidas ao banco de dados), adotando uma abordagem mais eficiente e escalável baseada em eventos.

## Principais Tecnologias
- Java 17
- Spring Boot
- RabbitMQ
- Plugin rabbitmq-delayed-message-exchange
- Docker & Docker Compose

## Como funciona

1. **Envio da Notificação**: Ao enviar uma notificação, o sistema agenda uma mensagem de timeout no RabbitMQ usando o plugin rabbitmq-delayed-message-exchange.
2. **Resposta do Usuário**: Se o usuário responde via webhook antes do timeout, o status da notificação é atualizado para `RESPONDIDO` no banco de dados.
3. **Timeout**: Se o tempo se esgota, o listener da aplicação processa a mensagem de timeout e altera o status da notificação para `EXPIRADO`.

## Arquitetura

- O ambiente RabbitMQ é totalmente automatizado via Docker e Docker Compose, incluindo a instalação do plugin necessário.
- O sistema é escalável, processa eventos em tempo real e utiliza recursos de forma eficiente.

## Como executar

1. Clone o repositório:
   ```sh
   git clone https://github.com/leandrosnazareth/notificacoes-rabbitmq.git
   ```
2. Suba o ambiente RabbitMQ com Docker Compose:
   ```sh
   cd rabbitmq
   docker compose up -d
   ```
   Isso irá:
   - Construir a imagem customizada do RabbitMQ usando o `Dockerfile`.
   - Instalar automaticamente o plugin `rabbitmq_delayed_message_exchange-3.13.0.ez`.
   - Subir o container configurado conforme o `docker-compose.yaml`.

3. Execute a aplicação Spring Boot (na raiz do projeto):
   ```sh
   ./mvnw spring-boot:run
   ```

## Endpoints principais

- `POST /notificacoes/enviar`: Cria e agenda uma notificação.
- `POST /notificacoes/webhook/resposta`: Recebe e processa a resposta do usuário.

## Observações

- O plugin `rabbitmq-delayed-message-exchange` é instalado automaticamente no ambiente Docker.
- O sistema evita polling, tornando o processamento de notificações mais eficiente.

## Autor
Leandro Nazareth

---

Sinta-se à vontade para contribuir ou abrir issues!

# 1. Usar a imagem oficial do RabbitMQ 3.13 como base.
#    É uma boa prática fixar a versão para evitar quebras futuras.
FROM rabbitmq:3.13-management

# 2. Baixar o arquivo do plugin de delayed messages para a pasta de plugins.
#    O comando RUN é executado durante a construção da imagem.
RUN wget https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.13.0/rabbitmq_delayed_message_exchange-3.13.0.ez -P /plugins/

# 3. Copiar nosso arquivo local 'enabled_plugins' para a pasta de configuração do RabbitMQ dentro da imagem.
#    Isso garante que o plugin será ativado quando o contêiner iniciar.
COPY enabled_plugins /etc/rabbitmq/
package com.leandronazareth.notificacoes_rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Classe de configuração do RabbitMQ para definir filas, exchanges e bindings.
public class RabbitMQConfig {

    public static final String TIMEOUT_EXCHANGE_NAME = "notificacao.timeout.exchange";
    public static final String TIMEOUT_QUEUE_NAME = "notificacao.timeout.queue";
    public static final String ROUTING_KEY = "notificacao.timeout.key";

    @Bean
    // Cria a fila que será utilizada para notificações de timeout.
    public Queue timeoutQueue() {
        return new Queue(TIMEOUT_QUEUE_NAME);
    }

    @Bean
    // Cria um exchange customizado do tipo "x-delayed-message" para suportar mensagens com atraso.
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(TIMEOUT_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    // Realiza o binding entre a fila e o exchange usando a routing key definida.
    public Binding binding(Queue queue, CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }
}
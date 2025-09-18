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
public class RabbitMQConfig {

    public static final String TIMEOUT_EXCHANGE_NAME = "notificacao.timeout.exchange";
    public static final String TIMEOUT_QUEUE_NAME = "notificacao.timeout.queue";
    public static final String ROUTING_KEY = "notificacao.timeout.key";

    @Bean
    public Queue timeoutQueue() {
        return new Queue(TIMEOUT_QUEUE_NAME);
    }

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(TIMEOUT_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding binding(Queue queue, CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }
}
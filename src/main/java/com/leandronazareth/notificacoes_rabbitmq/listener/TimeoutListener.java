package com.leandronazareth.notificacoes_rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leandronazareth.notificacoes_rabbitmq.config.RabbitMQConfig;
import com.leandronazareth.notificacoes_rabbitmq.repository.NotificacaoRepository;

@Component
public class TimeoutListener {

    @Autowired
    private NotificacaoRepository repository;

    @RabbitListener(queues = RabbitMQConfig.TIMEOUT_QUEUE_NAME)
    public void handleTimeout(Long id) {
        System.out.printf("[LISTENER] Mensagem de timeout recebida para a notificação %d.%n", id);

        repository.findById(id).ifPresent(notificacao -> {
            // A verificação mais importante: só altera se AINDA estiver PENDENTE
            if ("PENDENTE".equals(notificacao.getStatus())) {
                notificacao.setStatus("EXPIRADO");
                repository.save(notificacao);
                System.out.printf("[LISTENER] Notificação %d marcada como EXPIRADA.%n", id);
            } else {
                System.out.printf("[LISTENER] Notificação %d já foi processada (status: %s). Nenhuma ação.%n",
                        id, notificacao.getStatus());
            }
        });
    }
}
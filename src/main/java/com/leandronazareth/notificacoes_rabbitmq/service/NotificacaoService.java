package com.leandronazareth.notificacoes_rabbitmq.service;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandronazareth.notificacoes_rabbitmq.config.RabbitMQConfig;
import com.leandronazareth.notificacoes_rabbitmq.model.Notificacao;
import com.leandronazareth.notificacoes_rabbitmq.repository.NotificacaoRepository;

@Service
// Serviço responsável pela lógica de criação, agendamento de timeout e processamento de respostas das notificações.
public class NotificacaoService {

    // Para o teste, vamos usar 1 minuto (60.000 ms) em vez de 15.
    private static final int TIMEOUT_DELAY_MS = 60 * 1000;

    @Autowired
    // Repositório para persistência das notificações.
    private NotificacaoRepository repository;

    @Autowired
    // Template para envio de mensagens ao RabbitMQ.
    private RabbitTemplate rabbitTemplate;

    /**
     * Cria uma notificação com status PENDENTE e agenda o timeout via RabbitMQ.
     * @param destinatario destinatário da notificação
     * @return notificação criada e salva
     */
    public Notificacao criarEAgendarTimeout(String destinatario) {
        // 1. Cria e salva a notificação com status PENDENTE
        Notificacao notificacao = new Notificacao();
        notificacao.setDestinatario(destinatario);
        notificacao.setStatus("PENDENTE");
        notificacao.setDataCriacao(LocalDateTime.now());
        Notificacao notificacaoSalva = repository.save(notificacao);

        System.out.printf("[SERVICE] Notificação %d criada para %s. Agendando timeout.%n",
                notificacaoSalva.getId(), destinatario);

        // 2. Envia a mensagem com atraso para o RabbitMQ
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TIMEOUT_EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                notificacaoSalva.getId(), // O corpo da mensagem é apenas o ID
                message -> {
                    message.getMessageProperties().setHeader("x-delay", TIMEOUT_DELAY_MS);
                    return message;
                });

        return notificacaoSalva;
    }

    /**
     * Processa a resposta recebida para uma notificação.
     * Só altera o status se ainda estiver pendente.
     * @param id identificador da notificação
     * @param resposta conteúdo da resposta recebida
     * @return notificação atualizada ou nulo se não encontrada
     */
    public Notificacao processarResposta(Long id, String resposta) {
        return repository.findById(id).map(notificacao -> {
            // Só processa se ainda estiver pendente
            if ("PENDENTE".equals(notificacao.getStatus())) {
                notificacao.setStatus("RESPONDIDO");
                notificacao.setResposta(resposta);
                notificacao.setDataResposta(LocalDateTime.now());
                System.out.printf("[SERVICE] Resposta recebida para a notificação %d.%n", id);
                return repository.save(notificacao);
            }
            System.out.printf("[SERVICE] Resposta para a notificação %d ignorada (status: %s).%n",
                    id, notificacao.getStatus());
            return notificacao;
        }).orElse(null); // Retorna nulo se não encontrar
    }
}
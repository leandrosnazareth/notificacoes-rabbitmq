package com.leandronazareth.notificacoes_rabbitmq.dto;

import lombok.Data;

@Data
// DTO para receber os dados da resposta do webhook de notificação.
public class RespostaWebhookPayload {
    // ID da notificação que está sendo respondida.
    private Long idNotificacao;
    // Conteúdo da resposta recebida.
    private String resposta;
}

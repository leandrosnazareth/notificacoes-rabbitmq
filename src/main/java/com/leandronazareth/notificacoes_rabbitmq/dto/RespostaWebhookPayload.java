package com.leandronazareth.notificacoes_rabbitmq.dto;

import lombok.Data;

@Data
public class RespostaWebhookPayload {
    private Long idNotificacao;
    private String resposta;
}

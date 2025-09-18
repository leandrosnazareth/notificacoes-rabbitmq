package com.leandronazareth.notificacoes_rabbitmq.dto;

import lombok.Data;

@Data
// DTO para receber os dados necessários para criar uma notificação.
public class CriarNotificacaoRequest {
    // Destinatário da notificação.
    private String destinatario;
}
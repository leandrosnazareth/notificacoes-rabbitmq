package com.leandronazareth.notificacoes_rabbitmq.dto;

import lombok.Data;

@Data
public class CriarNotificacaoRequest {
    private String destinatario;
}
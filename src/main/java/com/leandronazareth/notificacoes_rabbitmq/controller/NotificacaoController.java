package com.leandronazareth.notificacoes_rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandronazareth.notificacoes_rabbitmq.dto.CriarNotificacaoRequest;
import com.leandronazareth.notificacoes_rabbitmq.dto.RespostaWebhookPayload;
import com.leandronazareth.notificacoes_rabbitmq.model.Notificacao;
import com.leandronazareth.notificacoes_rabbitmq.service.NotificacaoService;

@RestController
// Controller responsável pelo gerenciamento das notificações via API REST.
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    // Injeta o serviço de notificações.
    private NotificacaoService service;

    @PostMapping("/enviar")
    /**
     * Endpoint para criar e agendar uma notificação.
     * @param request dados do destinatário
     * @return resposta informando o ID da notificação criada
     */
    public ResponseEntity<String> enviar(@RequestBody CriarNotificacaoRequest request) {
        Notificacao notificacao = service.criarEAgendarTimeout(request.getDestinatario());
        return ResponseEntity.accepted().body("Notificação criada com ID: " + notificacao.getId());
    }

    @PostMapping("/webhook/resposta")
    /**
     * Endpoint para receber e processar respostas de webhook.
     * @param payload dados da resposta do webhook
     * @return resposta de sucesso ou not found
     */
    public ResponseEntity<String> webhook(@RequestBody RespostaWebhookPayload payload) {
        Notificacao notificacao = service.processarResposta(payload.getIdNotificacao(), payload.getResposta());
        if (notificacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Webhook processado.");
    }
}
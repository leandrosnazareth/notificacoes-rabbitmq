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
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody CriarNotificacaoRequest request) {
        Notificacao notificacao = service.criarEAgendarTimeout(request.getDestinatario());
        return ResponseEntity.accepted().body("Notificação criada com ID: " + notificacao.getId());
    }

    @PostMapping("/webhook/resposta")
    public ResponseEntity<String> webhook(@RequestBody RespostaWebhookPayload payload) {
        Notificacao notificacao = service.processarResposta(payload.getIdNotificacao(), payload.getResposta());
        if (notificacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Webhook processado.");
    }
}
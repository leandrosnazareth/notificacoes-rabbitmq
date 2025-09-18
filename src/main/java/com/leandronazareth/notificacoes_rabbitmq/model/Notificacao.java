package com.leandronazareth.notificacoes_rabbitmq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
// Entidade que representa uma notificação no sistema.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Identificador único da notificação.
    private Long id;
    // Destinatário da notificação.
    private String destinatario;
    // Status da notificação: PENDENTE, RESPONDIDO ou EXPIRADO.
    private String status; // PENDENTE, RESPONDIDO, EXPIRADO
    // Resposta recebida do destinatário.
    private String resposta;
    // Data de criação da notificação.
    private LocalDateTime dataCriacao;
    // Data em que a resposta foi recebida.
    private LocalDateTime dataResposta;
}
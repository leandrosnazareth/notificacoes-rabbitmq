package com.leandronazareth.notificacoes_rabbitmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leandronazareth.notificacoes_rabbitmq.model.Notificacao;

@Repository
// Interface de acesso ao banco de dados para a entidade Notificacao.
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
}
package org.example.demo3.audit;

/**
 * Tipos de ação registrados no log de auditoria.
 * Os nomes devem coincidir com o ENUM da coluna "acao" na tabela audit_log.
 */
public enum AcaoAuditoria {
    CRIAR,
    EDITAR,
    REMOVER
}

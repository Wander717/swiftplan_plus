package org.example.demo3.audit;

import org.example.demo3.UsuarioAtual;
import org.example.demo3.dao.AuditLogDAO;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registra no banco as ações realizadas pelo usuário logado.
 *
 * Uso: chamar após a operação de negócio ser concluída com sucesso, por exemplo:
 *   AuditoriaService.registrar(AcaoAuditoria.CRIAR,
 *       "criou o Tema \"Introdução à POO\" para a Disciplina \"Programação I\"");
 *
 * Falhas de auditoria nunca interrompem a operação principal: são apenas reportadas.
 */
public final class AuditoriaService {
    private static final Logger log = LoggerFactory.getLogger(AuditoriaService.class);

    private static final AuditLogDAO dao = new AuditLogDAO();

    private AuditoriaService() {
    }

    public static void registrar(AcaoAuditoria acao, String descricao) {
        UsuarioAtual sessao = UsuarioAtual.getInstancia();
        Integer usuarioId = sessao.getId_usuario();

        if (usuarioId == null) {
            // Sem usuário logado não há quem registrar; a ação não é auditada.
            log.warn("Auditoria ignorada (nenhum usuário logado): " + descricao);
            return;
        }

        try {
            dao.inserir(usuarioId, sessao.getTipo(), acao, descricao);
        } catch (SQLException | RuntimeException e) {
            // RuntimeException também é capturada: a auditoria nunca pode quebrar a operação principal.
            log.error("Falha ao registrar auditoria: " + e.getMessage());
        }
    }
}

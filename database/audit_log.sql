-- Tabela de auditoria (log de atividades) do Swiftplan+
-- Executar após a criação do banco swiftplan (tabela usuario já existente).

USE swiftplan;

CREATE TABLE IF NOT EXISTS audit_log (
    id_audit_log INT AUTO_INCREMENT,
    usuario_id INT NULL,                              -- quem realizou a ação (NULL se o usuário for removido)
    usuario_nome VARCHAR(120) NOT NULL,               -- cópia do nome no momento da ação
    tipo_usuario ENUM('ADM', 'COORD', 'PROF') NULL,   -- perfil com o qual a ação foi realizada
    acao ENUM('CRIAR', 'EDITAR', 'REMOVER') NOT NULL,
    descricao VARCHAR(500) NOT NULL,                  -- texto legível da ação
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id_audit_log),
    INDEX idx_audit_data_hora (data_hora),
    CONSTRAINT fk_audit_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuario (id_usuario) ON DELETE SET NULL
);

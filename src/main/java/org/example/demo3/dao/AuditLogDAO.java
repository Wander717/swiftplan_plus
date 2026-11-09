package org.example.demo3.dao;

import org.example.demo3.DatabaseConnection;
import org.example.demo3.audit.AcaoAuditoria;
import org.example.demo3.entity.AuditLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditLogDAO {
    private static final Logger log = LoggerFactory.getLogger(AuditLogDAO.class);

    // OBTÉM A CONEXÃO OU FALHA DE FORMA CONTROLADA
    // DatabaseConnection.getConnection() devolve null quando não há banco disponível.
    private static Connection obterConexao() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Sem conexão com o banco de dados.");
        }
        return conn;
    }

    // INSERE UM REGISTRO DE AUDITORIA
    // O nome do usuário é copiado da tabela usuario no momento da ação.
    public void inserir(Integer usuarioId, String tipoUsuario, AcaoAuditoria acao, String descricao) throws SQLException {
        String sql = """
            INSERT INTO audit_log (usuario_id, usuario_nome, tipo_usuario, acao, descricao)
            SELECT u.id_usuario, u.nome, ?, ?, ?
            FROM usuario u
            WHERE u.id_usuario = ?
            """;

        try (Connection conn = obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tipoUsuario);
            ps.setString(2, acao.name());
            ps.setString(3, descricao);
            ps.setInt(4, usuarioId);
            ps.executeUpdate();
        }
    }

    // LISTA OS REGISTROS DO MAIS RECENTE PARA O MAIS ANTIGO
    public List<AuditLog> listarRecentes(int limite) {
        List<AuditLog> registros = new ArrayList<>();
        String sql = """
            SELECT * FROM audit_log
            ORDER BY data_hora DESC, id_audit_log DESC
            LIMIT ?
            """;

        try (Connection conn = obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuditLog registro = new AuditLog();
                    registro.setId_audit_log(rs.getInt("id_audit_log"));
                    int usuarioId = rs.getInt("usuario_id");
                    registro.setUsuario_id(rs.wasNull() ? null : usuarioId);
                    registro.setUsuario_nome(rs.getString("usuario_nome"));
                    registro.setTipo_usuario(rs.getString("tipo_usuario"));
                    registro.setAcao(AcaoAuditoria.valueOf(rs.getString("acao")));
                    registro.setDescricao(rs.getString("descricao"));
                    Timestamp ts = rs.getTimestamp("data_hora");
                    registro.setData_hora(ts == null ? null : ts.toLocalDateTime());
                    registros.add(registro);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao listar auditoria: " + e.getMessage());
        }
        return registros;
    }
}

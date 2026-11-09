package org.example.demo3.entity;

import org.example.demo3.audit.AcaoAuditoria;

import java.time.LocalDateTime;

public class AuditLog {
    private Integer id_audit_log;
    private Integer usuario_id;
    private String usuario_nome;
    private String tipo_usuario;
    private AcaoAuditoria acao;
    private String descricao;
    private LocalDateTime data_hora;

    public AuditLog() {
    }

    public Integer getId_audit_log() { return id_audit_log; }
    public void setId_audit_log(Integer id_audit_log) { this.id_audit_log = id_audit_log; }

    public Integer getUsuario_id() { return usuario_id; }
    public void setUsuario_id(Integer usuario_id) { this.usuario_id = usuario_id; }

    public String getUsuario_nome() { return usuario_nome; }
    public void setUsuario_nome(String usuario_nome) { this.usuario_nome = usuario_nome; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipo_usuario(String tipo_usuario) { this.tipo_usuario = tipo_usuario; }

    public AcaoAuditoria getAcao() { return acao; }
    public void setAcao(AcaoAuditoria acao) { this.acao = acao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getData_hora() { return data_hora; }
    public void setData_hora(LocalDateTime data_hora) { this.data_hora = data_hora; }
}

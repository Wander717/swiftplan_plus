package org.example.demo3.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.demo3.dao.AuditLogDAO;
import org.example.demo3.entity.AuditLog;

import java.time.format.DateTimeFormatter;

/**
 * Tela somente leitura com o histórico de atividades (audit_log).
 * Acessível apenas pelo perfil ADM.
 */
public class AuditLogController {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final int LIMITE_REGISTROS = 500;
    private static final String TODAS_AS_ACOES = "Todas";

    @FXML private TextField tfBusca;
    @FXML private ComboBox<String> cbAcao;
    @FXML private Label lblTotal;
    @FXML private TableView<AuditLog> tabelaAuditoria;
    @FXML private TableColumn<AuditLog, String> colDataHora;
    @FXML private TableColumn<AuditLog, String> colUsuario;
    @FXML private TableColumn<AuditLog, String> colPerfil;
    @FXML private TableColumn<AuditLog, String> colAcao;
    @FXML private TableColumn<AuditLog, String> colDescricao;

    private final AuditLogDAO dao = new AuditLogDAO();
    private final ObservableList<AuditLog> registros = FXCollections.observableArrayList();
    private FilteredList<AuditLog> registrosFiltrados;

    @FXML
    public void initialize() {
        cbAcao.getItems().addAll(TODAS_AS_ACOES, "CRIAR", "EDITAR", "REMOVER");
        cbAcao.getSelectionModel().selectFirst();

        colDataHora.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getData_hora() == null ? "" : c.getValue().getData_hora().format(FORMATO_DATA_HORA)));
        colUsuario.setCellValueFactory(c -> new SimpleStringProperty(texto(c.getValue().getUsuario_nome())));
        colPerfil.setCellValueFactory(c -> new SimpleStringProperty(texto(c.getValue().getTipo_usuario())));
        colAcao.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getAcao() == null ? "" : c.getValue().getAcao().name()));
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(texto(c.getValue().getDescricao())));

        registrosFiltrados = new FilteredList<>(registros, r -> true);
        tabelaAuditoria.setItems(registrosFiltrados);
        tabelaAuditoria.setPlaceholder(new Label("Nenhum registro encontrado."));

        tfBusca.textProperty().addListener((obs, antigo, novo) -> aplicarFiltro());
        cbAcao.valueProperty().addListener((obs, antigo, novo) -> aplicarFiltro());

        carregarRegistros();
    }

    @FXML
    private void handleAtualizar() {
        carregarRegistros();
    }

    private void carregarRegistros() {
        registros.setAll(dao.listarRecentes(LIMITE_REGISTROS));
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        if (registrosFiltrados == null) return;

        String termo = tfBusca.getText() == null ? "" : tfBusca.getText().trim().toLowerCase();
        String acao = cbAcao.getValue();

        registrosFiltrados.setPredicate(r ->
                (acao == null || TODAS_AS_ACOES.equals(acao)
                        || (r.getAcao() != null && r.getAcao().name().equals(acao)))
                && (termo.isEmpty()
                        || contem(r.getDescricao(), termo)
                        || contem(r.getUsuario_nome(), termo)));

        lblTotal.setText(registrosFiltrados.size() + " registro(s) exibido(s) de " + registros.size()
                + " carregado(s) (máximo " + LIMITE_REGISTROS + ", do mais recente ao mais antigo)");
    }

    private static boolean contem(String valor, String termo) {
        return valor != null && valor.toLowerCase().contains(termo);
    }

    private static String texto(String valor) {
        return valor == null ? "" : valor;
    }
}

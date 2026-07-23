package org.example.demo3;

import org.example.demo3.dao.SemestreLetivoDAO;
import org.example.demo3.dao.SprintDAO;
import org.example.demo3.entity.SemestreLetivo;
import org.example.demo3.entity.Sprint;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SemestreLetivoService {

    private final SemestreLetivoDAO semestreDAO = new SemestreLetivoDAO();
    private final SprintDAO sprintDAO = new SprintDAO();

    public void criarSemestre1DoAnoAtual(int criadoPorAdmId) throws SQLException {
        int ano = LocalDate.now().getYear();

        SemestreLetivo semestre = new SemestreLetivo();
        semestre.setCriado_por_adm_id(criadoPorAdmId);
        semestre.setAno(ano);
        semestre.setNumero_semestre(1);
        semestre.setData_inicio(LocalDate.of(ano, 2, 9));
        semestre.setData_fim(LocalDate.of(ano, 6, 19));
        semestre.setData_tg(LocalDate.of(ano, 6, 14));
        semestre.setData_feira(LocalDate.of(ano, 6, 17));

        int idSemestre = semestreDAO.inserirSemestre(semestre);

        List<Sprint> sprints = List.of(
                criarSprint(idSemestre, 1, ano, 2, 9, 3, 6, 3, 9),
                criarSprint(idSemestre, 2, ano, 3, 9, 4, 10, 4, 13),
                criarSprint(idSemestre, 3, ano, 4, 13, 6, 19, 6, 22)
        );

        for (Sprint sprint : sprints) {
            sprintDAO.inserirSprint(sprint);
        }
    }

    private Sprint criarSprint(int semestreId, int numero, int ano,
                               int mesInicio, int diaInicio,
                               int mesFim, int diaFim,
                               int mesReview, int diaReview) {

        Sprint sprint = new Sprint();
        sprint.setSemestre_letivo_id(semestreId);
        sprint.setNumero(numero);
        sprint.setData_inicio(LocalDate.of(ano, mesInicio, diaInicio));
        sprint.setData_fim(LocalDate.of(ano, mesFim, diaFim));
        sprint.setData_review(LocalDate.of(ano, mesReview, diaReview));
        return sprint;
    }
}

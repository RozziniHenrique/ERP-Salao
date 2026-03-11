package uscs.STEFER.domain.agendamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoRelatorioEspecialidade;
import uscs.STEFER.domain.dashboard.dto.AlertaQualidade;
import uscs.STEFER.domain.dashboard.dto.RankingFuncionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(Long idFuncionario, LocalDateTime data);

    boolean existsByClienteIdAndDataAndMotivoCancelamentoIsNull(Long idCliente, LocalDateTime data);

    Optional<Agendamento> findByIdAndMotivoCancelamentoIsNull(Long id);

    @Query("""
        SELECT a FROM Agendamento a
        WHERE (:data IS NULL OR DATE(a.data) = :data)
        AND (:idFunc IS NULL OR a.funcionario.id = :idFunc)
        AND (:idCli IS NULL OR a.cliente.id = :idCli)
        AND (:idEspec IS NULL OR a.especialidade.id = :idEspec)
        """)
Page<Agendamento> findAllComFiltros(
    @Param("data") LocalDate data, 
    @Param("idFunc") Long idFunc, 
    @Param("idCli") Long idCli, 
    @Param("idEspec") Long idEspec, 
    Pageable paginacao
);
   
@Query("""
                SELECT new uscs.STEFER.domain.agendamento.dto.dtoAgendamentoRelatorioEspecialidade(e.nome, COUNT(a))
                FROM Agendamento a
                JOIN a.especialidade e
                WHERE a.motivoCancelamento IS NULL
                AND a.concluido = true
                GROUP BY e.nome
                ORDER BY COUNT(a) DESC
            """)
    List<dtoAgendamentoRelatorioEspecialidade> contagemPorEspecialidade();


    @Query("""
                SELECT SUM(e.preco)
                FROM Agendamento a
                JOIN a.especialidade e
                WHERE a.concluido = true
                AND a.data BETWEEN :inicio AND :fim
            """)
    BigDecimal calcularFaturamentoTotal(LocalDateTime inicio, LocalDateTime fim);

    @Query("""
                SELECT new uscs.STEFER.domain.dashboard.dto.RankingFuncionario(f.nome, COUNT(a))
                FROM Agendamento a
                JOIN a.funcionario f
                GROUP BY f.nome
                ORDER BY COUNT(a) DESC
            """)
    List<RankingFuncionario> findRankingFuncionarios();

    @Query("""
                SELECT new uscs.STEFER.domain.dashboard.dto.AlertaQualidade(f.nome, AVG(a.nota))
                FROM Agendamento a
                JOIN a.funcionario f
                WHERE a.nota IS NOT NULL
                GROUP BY f.nome
                HAVING AVG(a.nota) < 4.0
                ORDER BY AVG(a.nota) ASC
            """)
    List<AlertaQualidade> findPioresAvaliados();

    @Query("SELECT a FROM Agendamento a WHERE a.funcionario.id = :id OR a.cliente.id = :id")
    Page<Agendamento> buscaPersonalizada(Long id, Pageable paginacao);
}

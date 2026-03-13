package erp.Salao.domain.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findAllByAtivoTrue(Pageable paginacao);

    Page<Funcionario> findAllByAtivoFalse(Pageable paginacao);

    Page<Funcionario> findAllByAtivoTrueAndEspecialidadesId(Long especialidadeId, Pageable paginacao);

    Page<Funcionario> findAllByAtivoFalseAndEspecialidadesId(Long especialidadeId, Pageable paginacao);

    @Query("""
            SELECT f FROM funcionarios f 
            JOIN Usuario u ON u.login = f.email
            WHERE f.ativo = :ativos
            AND u.role NOT IN ('ADMIN', 'RECEPCAO')
            AND (:idEspecialidade IS NULL OR EXISTS (
                SELECT e FROM f.especialidades e WHERE e.id = :idEspecialidade
            ))
            """)
    Page<Funcionario> buscarProfissionaisComFiltros(
        @Param("idEspecialidade") Long idEspecialidade, 
        @Param("ativos") boolean ativos, 
        Pageable paginacao
);
    
    @Query(value = """
                SELECT f.* FROM funcionarios f
                INNER JOIN funcionario_especialidade fe ON fe.funcionario_id = f.id
                WHERE f.ativo = 1
                AND fe.especialidade_id = :idEspecialidade
                AND f.id NOT IN (
                    SELECT a.funcionario_id FROM agendamentos a
                    WHERE a.data = :data
                    AND a.motivo_cancelamento IS NULL
                )
                ORDER BY RAND()
                LIMIT 1
            """, nativeQuery = true)
    Funcionario escolherFuncionarioAleatorioLivreNaData(Long idEspecialidade, LocalDateTime data);
}
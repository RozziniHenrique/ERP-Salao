package uscs.STEFER.domain.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findAllByAtivoTrue(Pageable paginacao);

    Page<Funcionario> findAllByAtivoFalse(Pageable paginacao);

    Page<Funcionario> findAllByAtivoTrueAndEspecialidadesId(Long especialidadeId, Pageable paginacao);

    Page<Funcionario> findAllByAtivoFalseAndEspecialidadesId(Long especialidadeId, Pageable paginacao);

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
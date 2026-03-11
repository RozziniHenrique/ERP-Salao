package uscs.STEFER.domain.especialidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Page<Especialidade> findAllByAtivoTrue(Pageable paginacao);

    Page<Especialidade> findAllByAtivoFalse(Pageable paginacao);
}

package uscs.STEFER.domain.avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Boolean existsByAgendamentoId(Long idAgendamento);

}

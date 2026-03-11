package uscs.STEFER.domain.agendamento.validacoes;

import org.springframework.stereotype.Component;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCadastrar;
import uscs.STEFER.infra.exception.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaMinima implements ValidadorAgendamento {
    public void validar(dtoAgendamentoCadastrar dados) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dados.data()).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("O agendamento deve ser feito com no mínimo 30 minutos de antecedência!");
        }
    }
}

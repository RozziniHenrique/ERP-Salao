package uscs.STEFER.domain.agendamento.validacoes;

import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCancelar;

public interface ValidadorCancelamentoAgendamento {
    void validar(dtoAgendamentoCancelar dados);
}

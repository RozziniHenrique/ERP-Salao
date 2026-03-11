package uscs.STEFER.domain.agendamento.validacoes;

import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCadastrar;

public interface ValidadorAgendamento {
    void validar(dtoAgendamentoCadastrar dados);
}

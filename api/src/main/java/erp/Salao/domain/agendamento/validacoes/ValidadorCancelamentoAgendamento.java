package erp.Salao.domain.agendamento.validacoes;

import erp.Salao.domain.agendamento.dto.dtoAgendamentoCancelar;

public interface ValidadorCancelamentoAgendamento {
    void validar(dtoAgendamentoCancelar dados);
}

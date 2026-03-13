package erp.Salao.domain.agendamento.dto;

import java.time.LocalDate;

public record dtoAgendamentoFiltro(
        LocalDate data,
        Long idFuncionario,
        Long idCliente
) {
}

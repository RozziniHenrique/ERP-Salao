package erp.Salao.domain.agendamento.dto;

import java.time.LocalDateTime;

import erp.Salao.domain.agendamento.Agendamento;

public record dtoAgendamentoDetalhar(
        Long id,
        Long idFuncionario,
        String nomeFuncionario,
        Long idCliente,
        String nomeCliente,
        Long idEspecialidade,
        String nomeEspecialista,
        LocalDateTime data) {
    public dtoAgendamentoDetalhar(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getFuncionario().getId(),
                agendamento.getFuncionario().getNome(),
                agendamento.getCliente().getId(),
                agendamento.getCliente().getNome(),
                agendamento.getEspecialidade().getId(),
                agendamento.getEspecialidade().getNome(),
                agendamento.getData());
    }
}

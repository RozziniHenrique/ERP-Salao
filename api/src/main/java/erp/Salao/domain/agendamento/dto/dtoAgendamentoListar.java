package erp.Salao.domain.agendamento.dto;

import java.time.LocalDateTime;

import erp.Salao.domain.agendamento.Agendamento;

public record dtoAgendamentoListar(
    Long id,
    String nomeCliente,
    String nomeFuncionario,
    String especialidade,
    LocalDateTime data,
    Boolean concluido,
    Integer nota,
    String motivoCancelamento
) {
    public dtoAgendamentoListar(Agendamento agendamento) {
        this(
            agendamento.getId(),
            agendamento.getCliente().getNome(),
            agendamento.getFuncionario().getNome(),
            agendamento.getEspecialidade().getNome(),
            agendamento.getData(),
            agendamento.getConcluido(),
            agendamento.getNota(),
            agendamento.getMotivoCancelamento()
        );
    }
}
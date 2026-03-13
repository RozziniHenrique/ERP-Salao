package erp.Salao.domain.agendamento.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.agendamento.dto.dtoAgendamentoCancelar;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoAgendamento {

    @Autowired
    private AgendamentoRepository repository;

    @Override
    public void validar(dtoAgendamentoCancelar dados) {
        var agendamento = repository.findById(dados.idAgendamento())
                .orElseThrow(() -> new ValidacaoException("Id do agendamento informado não existe!"));

        var agora = LocalDateTime.now();
        var dataAgendamento = agendamento.getData();
        var diferencaEmHoras = Duration.between(agora, dataAgendamento).toHours();

        if (diferencaEmHoras < 2) {
            throw new ValidacaoException("Agendamento somente pode ser cancelado com antecedência mínima de 2h!");
        }
    }
}

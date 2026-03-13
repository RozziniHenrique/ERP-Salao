package erp.Salao.domain.agendamento.validacoes;

import org.springframework.stereotype.Component;

import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {

    public void validar(dtoAgendamentoCadastrar dados) {
        var data = dados.data();
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.getHour() < 9;
        var depoisDoEncerramento = data.getHour() > 21;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidacaoException("Fora do horário de funcionamento (Segunda a Sábado, 09:00 às 22:00)");
        }
    }
}

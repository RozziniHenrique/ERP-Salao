package erp.Salao.domain.agendamento.validacoes;

import org.springframework.stereotype.Component;

import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaMinima implements ValidadorAgendamento {
    public void validar(dtoAgendamentoCadastrar dados) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dados.data()).toMinutes();

        if(dados.idCliente() != 1){
            if (diferencaEmMinutos < 30) {
                throw new ValidacaoException("O agendamento deve ser feito com no mínimo 30 minutos de antecedência!");
            } 
        }
    }
}

package uscs.STEFER.domain.agendamento.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCadastrar;
import uscs.STEFER.infra.exception.ValidacaoException;

@Component
public class ValidadorDuplicidadeAgendamentoFuncionario implements ValidadorAgendamento {
    @Autowired
    private AgendamentoRepository repository;

    public void validar(dtoAgendamentoCadastrar dados) {
        if (dados.idFuncionario() == null) return;
        var possuiAgendamento = repository.existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(dados.idFuncionario(), dados.data());
        if (possuiAgendamento) {
            throw new ValidacaoException("Funcionário já possui outro agendamento nesse mesmo horário!");
        }
    }
}

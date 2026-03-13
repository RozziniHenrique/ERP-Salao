package erp.Salao.domain.agendamento.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.infra.exception.ValidacaoException;

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

package erp.Salao.model.Agendamento.validacao.agendamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.domain.agendamento.validacoes.ValidadorDuplicidadeAgendamentoFuncionario;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ValidadorDuplicidadeAgendamentoFuncionarioTest {
    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private ValidadorDuplicidadeAgendamentoFuncionario validador;

    @Test
    @DisplayName("Cenário 1: Funcionário já possui agendamento no mesmo horário -> Deve lançar exceção")
    void validar_cenario01() {
        var idFuncionario = 1L;
        var data = LocalDateTime.now().plusDays(1);
        var dados = new dtoAgendamentoCadastrar(1L, idFuncionario, 1L, data);

        when(agendamentoRepository.existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(idFuncionario, data))
                .thenReturn(true);

        var exception = assertThrows(ValidacaoException.class, () -> validador.validar(dados));
        assertThat(exception.getMessage().contains("Já possui outro agendamento"));
    }

    @Test
    @DisplayName("Cenário 2: Funcionário livre no horário -> Não deve lançar exceção")
    void validar_cenario02() {
        var idFuncionario = 1L;
        var data = LocalDateTime.now().plusDays(1);
        var dados = new dtoAgendamentoCadastrar(1L, idFuncionario, 1L, data);
        when(agendamentoRepository.existsByFuncionarioIdAndDataAndMotivoCancelamentoIsNull(idFuncionario, data))
                .thenReturn(false);

        assertDoesNotThrow(() -> validador.validar(dados));
    }
}
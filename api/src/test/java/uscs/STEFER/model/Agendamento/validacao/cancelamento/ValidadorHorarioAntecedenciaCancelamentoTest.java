package uscs.STEFER.model.Agendamento.validacao.cancelamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import uscs.STEFER.domain.agendamento.Agendamento;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCancelar;
import uscs.STEFER.domain.agendamento.validacoes.ValidadorHorarioAntecedenciaCancelamento;
import uscs.STEFER.infra.exception.ValidacaoException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ValidadorHorarioAntecedenciaCancelamentoTest {
    @Mock
    private AgendamentoRepository agendamentoRepository;
    @InjectMocks
    private ValidadorHorarioAntecedenciaCancelamento validador;

    @Test
    @DisplayName("Cenário 1: Cancelamento com menos de 2 horas de antecedência -> Erro")
    void validar_cenario01() {
        var agora = LocalDateTime.now();
        var dataAgendamento = agora.plusHours(1);
        var idAgendamento = 1L;

        var agendamento = new Agendamento();
        agendamento.setData(dataAgendamento);

        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.of(agendamento));

        var dados = new dtoAgendamentoCancelar(idAgendamento, "Desistência");

        var exception = assertThrows(ValidacaoException.class, () -> validador.validar(dados));

        assertThat(exception.getMessage()).contains("antecedência mínima de 2h");
    }

    @Test
    @DisplayName("Cenário 2: Cancelamento com mais de 2 horas de antecedência -> Sucesso")
    void validar_cenario02() {
        var dataAgendamento = LocalDateTime.now().plusHours(3);
        var idAgendamento = 1L;

        var agendamento = new Agendamento();
        agendamento.setData(dataAgendamento);

        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.of(agendamento));

        var dados = new dtoAgendamentoCancelar(idAgendamento, "Mudança de planos");

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Cenário 3: Cancelamento com mais de 24h de antecedência -> Sucesso")
    void validar_cenario03() {
        var dataAgendamento = LocalDateTime.now().plusDays(2);
        var idAgendamento = 1L;
        var agendamento = new Agendamento();
        agendamento.setData(dataAgendamento);

        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.of(agendamento));

        var dados = new dtoAgendamentoCancelar(idAgendamento, "O cliente desistiu");

        assertDoesNotThrow(() -> validador.validar(dados));
    }
}
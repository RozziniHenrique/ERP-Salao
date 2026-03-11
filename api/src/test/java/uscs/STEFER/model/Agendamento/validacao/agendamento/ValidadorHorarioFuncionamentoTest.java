package uscs.STEFER.model.Agendamento.validacao.agendamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import uscs.STEFER.domain.agendamento.dto.dtoAgendamentoCadastrar;
import uscs.STEFER.domain.agendamento.validacoes.ValidadorHorarioFuncionamento;
import uscs.STEFER.infra.exception.ValidacaoException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class ValidadorHorarioFuncionamentoTest {

    private ValidadorHorarioFuncionamento validador = new ValidadorHorarioFuncionamento();

    @Test
    @DisplayName("Cenário 1: Agendamento em um domingo -> Deve lançar exceção")
    void validar_cenario01() {
        var domingo = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                .withHour(10).withMinute(0);

        var dados = new dtoAgendamentoCadastrar(1l, 1l, 1l, domingo);

        var exception = assertThrows(ValidacaoException.class, () -> validador.validar(dados));
        assertThat(exception.getMessage().contains("Fora do horário de funcionamento"));
    }

    @Test
    @DisplayName("Cenário 2: Agendamento antes da abertura(6h) -> Deve lançar exceção")
    void validar_cenario02() {
        var segundaMuitoCedo = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(6).withMinute(0);

        var dados = new dtoAgendamentoCadastrar(1L, 1L, 1L, segundaMuitoCedo);

        var exception = assertThrows(ValidacaoException.class, () -> validador.validar(dados));
        assertThat(exception.getMessage()).contains("Fora do horário de funcionamento");
    }

    @Test
    @DisplayName("Cenário 3: Agendamento em horário válido -> NÃO deve lançar exceção")
    void validar_cenario03() {
        var segundaHorarioComercial = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0);

        var dados = new dtoAgendamentoCadastrar(1L, 1L, 1L, segundaHorarioComercial);
        assertDoesNotThrow(() -> validador.validar(dados));
    }
}



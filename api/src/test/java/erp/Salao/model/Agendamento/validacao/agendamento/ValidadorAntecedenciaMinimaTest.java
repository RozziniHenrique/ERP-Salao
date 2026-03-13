package erp.Salao.model.Agendamento.validacao.agendamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.domain.agendamento.validacoes.ValidadorAntecedenciaMinima;
import erp.Salao.infra.exception.ValidacaoException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("test")
class ValidadorAntecedenciaMinimaTest {

    private ValidadorAntecedenciaMinima validador = new ValidadorAntecedenciaMinima();

    @Test
    @DisplayName("Cenário 1: Agendamento com menos de 30 minutos de antecedência -> Erro")
    void validar_cenario01() {
        var agoraMais20Minutos = LocalDateTime.now().plusMinutes(20);
        var dados = new dtoAgendamentoCadastrar(1L, 1L, 1L, agoraMais20Minutos);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }

    @Test
    @DisplayName("Cenário 2: Agendamento com antecedência correta -> Sucesso")
    void validar_cenario02() {
        var agoraMais40Minutos = LocalDateTime.now().plusMinutes(40);
        var dados = new dtoAgendamentoCadastrar(1L, 1L, 1L, agoraMais40Minutos);

        assertDoesNotThrow(() -> validador.validar(dados));
    }
}

package uscs.STEFER.domain.agendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record dtoAgendamentoCancelar(
        @NotNull
        Long idAgendamento,

        @NotBlank
        String motivo
) {
}

package uscs.STEFER.domain.agendamento.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record dtoAgendamentoFinalizar(
        @NotNull Long id,
        @Min(1) @Max(5) Integer nota
) {
}

package uscs.STEFER.domain.avaliacao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record dtoAvaliacaoCadastrar(
        @NotNull @Min(1) @Max(5) Integer nota,
        String comentario
) {
}

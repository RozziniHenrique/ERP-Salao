package erp.Salao.domain.especialidade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record dtoEspecialidadeCadastrar(

        @NotBlank
        String nome,

        @NotNull
        @Positive
        BigDecimal preco,

        String descricao) {
}
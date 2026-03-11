package uscs.STEFER.domain.cliente.dto;

import jakarta.validation.constraints.NotNull;
import uscs.STEFER.domain.endereco.dtoEndereco;

public record dtoClienteAtualizar(

        @NotNull
        Long id,
        String nome,
        String telefone,
        dtoEndereco endereco) {
}

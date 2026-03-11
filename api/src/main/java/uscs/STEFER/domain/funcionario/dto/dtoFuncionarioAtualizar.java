package uscs.STEFER.domain.funcionario.dto;

import jakarta.validation.constraints.NotNull;
import uscs.STEFER.domain.endereco.dtoEndereco;

import java.util.List;

public record dtoFuncionarioAtualizar(
        @NotNull
        Long id,
        String nome,
        String telefone,
        dtoEndereco endereco,
        List<Long> especialidadesIds
) {
}

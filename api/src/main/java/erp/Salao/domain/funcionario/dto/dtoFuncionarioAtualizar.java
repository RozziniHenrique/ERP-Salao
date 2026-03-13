package erp.Salao.domain.funcionario.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import erp.Salao.domain.endereco.dtoEndereco;

public record dtoFuncionarioAtualizar(
        @NotNull
        Long id,
        String nome,
        String telefone,
        dtoEndereco endereco,
        List<Long> especialidadesIds
) {
}

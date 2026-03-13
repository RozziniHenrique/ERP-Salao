package erp.Salao.domain.cliente.dto;

import erp.Salao.domain.endereco.dtoEndereco;
import jakarta.validation.constraints.NotNull;

public record dtoClienteAtualizar(

        @NotNull
        Long id,
        String nome,
        String telefone,
        dtoEndereco endereco) {
}

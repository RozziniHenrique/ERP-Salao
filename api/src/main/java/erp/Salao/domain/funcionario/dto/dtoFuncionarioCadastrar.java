package erp.Salao.domain.funcionario.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

import erp.Salao.domain.endereco.dtoEndereco;
import erp.Salao.domain.usuario.UsuarioRole;

public record dtoFuncionarioCadastrar(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 11, message = "CPF deve ter exatamente 11 dígitos")
        String cpf,

        @NotNull @Valid
        dtoEndereco endereco,

        @NotEmpty(message = "O funcionário deve ter pelo menos uma especialidade")
        List<Long> especialidadesIds,

        @NotNull(message = "A role é obrigatória")
        UsuarioRole role
) {}

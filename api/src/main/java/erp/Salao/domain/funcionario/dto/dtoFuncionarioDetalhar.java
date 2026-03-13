package erp.Salao.domain.funcionario.dto;

import java.util.Set;
import java.util.stream.Collectors;

import erp.Salao.domain.endereco.Endereco;
import erp.Salao.domain.especialidade.dto.dtoEspecialidadeDetalhar;
import erp.Salao.domain.funcionario.Funcionario;

public record dtoFuncionarioDetalhar(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco,
        Set<dtoEspecialidadeDetalhar> especialidades
) {

    public dtoFuncionarioDetalhar(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCpf(),
                funcionario.getEndereco(),
                funcionario.getEspecialidades().stream()
                        .map(e -> new dtoEspecialidadeDetalhar(e))
                        .collect(Collectors.toSet())
        );
    }
}

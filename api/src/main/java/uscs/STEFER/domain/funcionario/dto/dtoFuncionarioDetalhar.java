package uscs.STEFER.domain.funcionario.dto;

import uscs.STEFER.domain.endereco.Endereco;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeDetalhar;
import uscs.STEFER.domain.funcionario.Funcionario;

import java.util.Set;
import java.util.stream.Collectors;

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

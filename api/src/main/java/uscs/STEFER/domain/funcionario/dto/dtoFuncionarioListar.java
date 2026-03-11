package uscs.STEFER.domain.funcionario.dto;

import uscs.STEFER.domain.especialidade.Especialidade;
import uscs.STEFER.domain.funcionario.Funcionario;

import java.util.Set;

public record dtoFuncionarioListar(Long id, String nome, String email, Boolean ativo, Set<Especialidade> especialidades) {

    public dtoFuncionarioListar(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getAtivo(),
                funcionario.getEspecialidades());
    }
}
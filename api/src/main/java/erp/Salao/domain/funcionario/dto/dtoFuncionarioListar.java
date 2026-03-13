package erp.Salao.domain.funcionario.dto;

import java.util.Set;

import erp.Salao.domain.especialidade.Especialidade;
import erp.Salao.domain.funcionario.Funcionario;

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
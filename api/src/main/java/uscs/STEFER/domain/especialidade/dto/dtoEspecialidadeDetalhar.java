package uscs.STEFER.domain.especialidade.dto;

import java.math.BigDecimal;

import uscs.STEFER.domain.especialidade.Especialidade;

public record dtoEspecialidadeDetalhar(Long id, String nome, String descricao, BigDecimal preco) {
    public dtoEspecialidadeDetalhar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao(), especialidade.getPreco());
    }
}

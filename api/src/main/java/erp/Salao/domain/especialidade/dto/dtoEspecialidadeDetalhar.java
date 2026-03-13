package erp.Salao.domain.especialidade.dto;

import java.math.BigDecimal;

import erp.Salao.domain.especialidade.Especialidade;

public record dtoEspecialidadeDetalhar(Long id, String nome, String descricao, BigDecimal preco) {
    public dtoEspecialidadeDetalhar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao(), especialidade.getPreco());
    }
}

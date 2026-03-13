package erp.Salao.domain.especialidade.dto;

import java.math.BigDecimal;

import erp.Salao.domain.especialidade.Especialidade;

public record dtoEspecialidadeListar(Long id, String nome, String descricao, BigDecimal preco, Boolean ativo) {

    public dtoEspecialidadeListar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao(), especialidade.getPreco(), especialidade.getAtivo());
    }
}
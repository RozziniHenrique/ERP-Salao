package uscs.STEFER.domain.especialidade.dto;

import java.math.BigDecimal;

import uscs.STEFER.domain.especialidade.Especialidade;

public record dtoEspecialidadeListar(Long id, String nome, String descricao, BigDecimal preco, Boolean ativo) {

    public dtoEspecialidadeListar(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao(), especialidade.getPreco(), especialidade.getAtivo());
    }
}
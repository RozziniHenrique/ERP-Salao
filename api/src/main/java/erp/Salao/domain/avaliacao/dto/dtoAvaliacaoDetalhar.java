package erp.Salao.domain.avaliacao.dto;

import erp.Salao.domain.avaliacao.Avaliacao;

public record dtoAvaliacaoDetalhar(
        Long id,
        Integer nota,
        String comentario,
        Long idAgendamento,
        String nomeFuncionario
) {
    public dtoAvaliacaoDetalhar(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getAgendamento().getId(),
                avaliacao.getAgendamento().getFuncionario().getNome()
        );
    }
}

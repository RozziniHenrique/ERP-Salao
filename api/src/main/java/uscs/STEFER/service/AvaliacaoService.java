package uscs.STEFER.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.avaliacao.Avaliacao;
import uscs.STEFER.domain.avaliacao.AvaliacaoRepository;
import uscs.STEFER.domain.avaliacao.dto.dtoAvaliacaoCadastrar;
import uscs.STEFER.infra.exception.ValidacaoException;

@Service
public class AvaliacaoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public void registrarAvaliacao(Long id, dtoAvaliacaoCadastrar dados) {

        var agendamento = agendamentoRepository.getReferenceById(id);

        if (!agendamento.getConcluido()) {
            throw new ValidacaoException("O agendamento precisa estar CONCLUÍDO!");
        }

        var avaliacao = new Avaliacao(dados, agendamento);
        avaliacaoRepository.save(avaliacao);
    }
}

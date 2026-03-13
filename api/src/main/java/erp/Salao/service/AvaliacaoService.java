package erp.Salao.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.avaliacao.Avaliacao;
import erp.Salao.domain.avaliacao.AvaliacaoRepository;
import erp.Salao.domain.avaliacao.dto.dtoAvaliacaoCadastrar;
import erp.Salao.infra.exception.ValidacaoException;

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

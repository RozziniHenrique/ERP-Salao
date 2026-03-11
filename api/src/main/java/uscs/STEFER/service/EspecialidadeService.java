package uscs.STEFER.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.especialidade.Especialidade;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.especialidade.dto.*;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    @Transactional
    public Especialidade cadastrar(dtoEspecialidadeCadastrar dados) {
        var especialidade = new Especialidade(dados);
        return repository.save(especialidade);
    }

    @Transactional
    public Especialidade atualizar(dtoEspecialidadeAtualizar dados) {
        var especialidade = repository.getReferenceById(dados.id());
        especialidade.atualizar(dados);
        return especialidade;
    }

    public Page<dtoEspecialidadeListar> listar(Boolean ativos, Pageable paginacao) {
    if (ativos != null && !ativos) {
        return repository.findAllByAtivoFalse(paginacao).map(dtoEspecialidadeListar::new);
    }
    return repository.findAllByAtivoTrue(paginacao).map(dtoEspecialidadeListar::new);
}

    public Especialidade detalhar(Long id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public void excluir(Long id) {
        var especialidade = repository.getReferenceById(id);
        especialidade.excluir();
    }

    @Transactional
    public void reativar(Long id) {
        var especialidade = repository.getReferenceById(id);
        especialidade.reativar();
    }
}
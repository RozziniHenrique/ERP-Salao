package uscs.STEFER.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.especialidade.EspecialidadeRepository;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.funcionario.dto.*;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.domain.usuario.UsuarioRepository;
import uscs.STEFER.infra.exception.ValidacaoException;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario cadastrar(dtoFuncionarioCadastrar dados) {
        if (usuarioRepository.existsByLogin(dados.email())){
            throw new ValidacaoException("Email já cadastrado em outra conta");
        }
        // Lógica de geração de senha: 3 letras do nome + 3 números do CPF
        String prefixoNome = dados.nome().length() >= 3 ? dados.nome().substring(0, 3) : dados.nome();
        String prefixoCpf = dados.cpf().substring(0, 3);
        String senhaLimpa = prefixoNome.toLowerCase() + prefixoCpf;
        String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

        var usuario = new Usuario(null, dados.email(), senhaCriptografada, true, dados.role());
        usuarioRepository.save(usuario);

        var funcionario = new Funcionario(dados);
        funcionario.setUsuario(usuario);

        if (dados.especialidadesIds() != null && !dados.especialidadesIds().isEmpty()) {
        var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
        
        if (especialidades.size() != dados.especialidadesIds().size()) {
            throw new ValidacaoException("Uma ou mais especialidades informadas são inválidas.");
        }
        
        funcionario.getEspecialidades().addAll(especialidades);
    }

    return repository.save(funcionario);
}

    @Transactional
    public Funcionario atualizar(dtoFuncionarioAtualizar dados) {
        var funcionario = repository.getReferenceById(dados.id());
        funcionario.atualizarFuncionario(dados);

        if (dados.especialidadesIds() != null) {
            var especialidades = especialidadeRepository.findAllById(dados.especialidadesIds());
            funcionario.atualizarEspecialidades(especialidades);
        }
        return funcionario;
    }

    public Page<dtoFuncionarioListar> listar(Long idEspecialidade, Boolean ativos, Pageable paginacao) {
    boolean buscarAtivos = (ativos == null) ? true : ativos;

    if (idEspecialidade != null) {
        if (buscarAtivos) {
            return repository.findAllByAtivoTrueAndEspecialidadesId(idEspecialidade, paginacao)
                             .map(dtoFuncionarioListar::new);
        } else {
            return repository.findAllByAtivoFalseAndEspecialidadesId(idEspecialidade, paginacao)
                             .map(dtoFuncionarioListar::new);
        }
    }

    if (buscarAtivos) {
        return repository.findAllByAtivoTrue(paginacao).map(dtoFuncionarioListar::new);
    } else {
        return repository.findAllByAtivoFalse(paginacao).map(dtoFuncionarioListar::new);
    }
    }

    public Funcionario detalhar(Long id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public void excluir(Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.excluirFuncionario();
        if (funcionario.getUsuario() != null) {
            funcionario.getUsuario().setAtivo(false);
        }
    }

    @Transactional
    public void reativar(Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.reativarFuncionario();
        if (funcionario.getUsuario() != null) {
            funcionario.getUsuario().setAtivo(true);
        }
    }

    @Transactional
    public void removerEspecialidade(Long idFuncionario, Long idEspecialidade) {
        var funcionario = repository.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        var especialidade = especialidadeRepository.findById(idEspecialidade)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
        
        funcionario.removerEspecialidades(List.of(especialidade));
    }
}
package uscs.STEFER.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uscs.STEFER.domain.cliente.Cliente;
import uscs.STEFER.domain.cliente.ClienteRepository;
import uscs.STEFER.domain.cliente.dto.*;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.domain.usuario.UsuarioRepository;
import uscs.STEFER.domain.usuario.UsuarioRole;
import uscs.STEFER.infra.exception.ValidacaoException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Cliente cadastrar(dtoClienteCadastrar dados) {
        if (usuarioRepository.existsByLogin(dados.email())){
            throw new ValidacaoException("Email já cadastrado em outra conta");
        }
        var senhaSegura = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(null, dados.email(), senhaSegura, true, UsuarioRole.CLIENTE);
        usuarioRepository.save(usuario);

        var cliente = new Cliente(dados);
        cliente.setUsuario(usuario);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(dtoClienteAtualizar dados) {
        var cliente = clienteRepository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
        return cliente;
    }

    public Page<dtoClienteListar> listar(Pageable paginacao) {
        return clienteRepository.findAllByAtivoTrue(paginacao).map(dtoClienteListar::new);
    }

    public Cliente detalhar(Long id) {
        return clienteRepository.getReferenceById(id);
    }

    @Transactional
    public void excluir(Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        cliente.excluirCliente();

        if (cliente.getUsuario() != null) {
            cliente.getUsuario().setAtivo(false);
        }
    }

    @Transactional
    public void reativar(Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        cliente.reativarCliente();

        if (cliente.getUsuario() != null) {
            cliente.getUsuario().setAtivo(true);
        }
    }
}
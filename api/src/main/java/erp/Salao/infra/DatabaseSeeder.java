package erp.Salao.infra;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import erp.Salao.domain.cliente.Cliente;
import erp.Salao.domain.cliente.ClienteRepository;
import erp.Salao.domain.funcionario.Funcionario;
import erp.Salao.domain.funcionario.FuncionarioRepository;
import erp.Salao.domain.usuario.Usuario;
import erp.Salao.domain.usuario.UsuarioRepository;
import erp.Salao.domain.usuario.UsuarioRole;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        String emailAdmin = "admin@stefer.com";

        if (usuarioRepository.findByLogin(emailAdmin) == null) {

            System.out.println(">>> Seed: Criando usuário administrador padrão...");

            var funcionarioAdmin = new Funcionario();
            funcionarioAdmin.setNome("Administrador Sistema");
            funcionarioAdmin.setEmail(emailAdmin);
            funcionarioAdmin.setCpf("00000000000");
            funcionarioAdmin.setTelefone("11999999999");
            funcionarioAdmin.setAtivo(true);

            funcionarioRepository.save(funcionarioAdmin);

            var usuarioAdmin = new Usuario(
                    null,
                    emailAdmin,
                    passwordEncoder.encode("admin123"),
                    true,
                    UsuarioRole.ADMIN
            );

            usuarioRepository.save(usuarioAdmin);

            System.out.println(">>> Seed: Admin criado com sucesso! Login: " + emailAdmin + " | Senha: admin123");
        } else {
            System.out.println(">>> Seed: Admin já existe no banco. Pulando etapa.");

            
        }
        String nomeBalcao = "CLIENTE BALCÃO";
        
        if (clienteRepository.findByNome(nomeBalcao).isEmpty()) { 
            System.out.println(">>> Seed: Criando Cliente Balcão padrão...");

            var clienteBalcao = new Cliente();
            clienteBalcao.setNome(nomeBalcao);
            clienteBalcao.setEmail("balcao@sistema.com");
            clienteBalcao.setCpf("00000000000");
            clienteBalcao.setTelefone("00000000000");
            clienteBalcao.setAtivo(true);

            clienteRepository.save(clienteBalcao);
            System.out.println(">>> Seed: Cliente Balcão criado com sucesso!");
        }
    }
}

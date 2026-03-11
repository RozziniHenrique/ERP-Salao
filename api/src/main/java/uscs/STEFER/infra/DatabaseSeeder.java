package uscs.STEFER.infra;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.domain.usuario.UsuarioRepository;
import uscs.STEFER.domain.usuario.UsuarioRole;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

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
    }
}

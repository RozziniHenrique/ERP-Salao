package uscs.STEFER.model.Funcionario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import uscs.STEFER.domain.agendamento.Agendamento;
import uscs.STEFER.domain.cliente.Cliente;
import uscs.STEFER.domain.cliente.dto.dtoClienteCadastrar;
import uscs.STEFER.domain.endereco.dtoEndereco;
import uscs.STEFER.domain.especialidade.Especialidade;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeCadastrar;
import uscs.STEFER.domain.funcionario.Funcionario;
import uscs.STEFER.domain.funcionario.FuncionarioRepository;
import uscs.STEFER.domain.funcionario.dto.dtoFuncionarioCadastrar;
import uscs.STEFER.domain.usuario.UsuarioRole;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Cenário 1: Especialista indisponível -> Deve retornar null")
    void deveriaRetornarNull_cenario01() {
        var proximaSegunda10am = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0).withSecond(0).withNano(0);
        var especialidade = cadastrarEspecialidade("Manicure", "Unhas decoradas");
        var funcionarioLivre = funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(especialidade.getId(), proximaSegunda10am);
        assertThat(funcionarioLivre).isNull();
    }

    @Test
    @DisplayName("Cenário 2: Especialista disponível -> Deve retornar o especialista")
    void deveriaRetornarEspecialista_cenario02() {
        var proximaSegunda10am = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0).withSecond(0).withNano(0);

        var especialidade = cadastrarEspecialidade("Manicure", "Descricao");
        var funcionario = cadastrarFuncionario("Henrique Teste", "henrique@email.com", "12345678901", especialidade);
        em.flush();
        em.clear();
        var funcionarioLivre = funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(especialidade.getId(), proximaSegunda10am);
        assertThat(funcionarioLivre).isNotNull();
        assertThat(funcionarioLivre.getNome()).isEqualTo("Henrique Teste");
    }

    @Test
    @DisplayName("Cenário 3: Especialista ocupado na data -> Deve retornar null")
    void deveriaRetornarNull_cenario03() {
        var proximaSegunda10am = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0).withSecond(0).withNano(0);

        var especialidade = cadastrarEspecialidade("Manicure", "Descricao");
        var funcionario = cadastrarFuncionario("Henrique Ocupado", "henrique@email.com", "12345678901", especialidade);
        var cliente = cadastrarCliente("Cliente Teste", "cliente@email.com", "SenhaTeste", "98765432100", "00000000000");

        agendar(funcionario, cliente, especialidade, proximaSegunda10am);
        em.flush();
        em.clear();
        var funcionarioLivre = funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(especialidade.getId(), proximaSegunda10am);
        assertThat(funcionarioLivre).isNull();

    }

    @Test
    @DisplayName("Cenário 4: Especialista disponível mas com especialidade diferente -> Deve retornar null")
    void deveriaRetornarNull_cenario04() {
        var proximaSegunda10am = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0).withSecond(0).withNano(0);
        var especialidadeBuscada = cadastrarEspecialidade("Manicure", "Descricao");
        var especialidadeDoFuncionario = cadastrarEspecialidade("Depilação", "Descricao");
        em.flush();
        em.clear();
        cadastrarFuncionario("Henrique Especialista em Outra Coisa", "henrique@email.com", "12345678901", especialidadeDoFuncionario);
        var funcionarioLivre = funcionarioRepository.escolherFuncionarioAleatorioLivreNaData(especialidadeBuscada.getId(), proximaSegunda10am);

        assertThat(funcionarioLivre).isNull();
    }

    private void agendar(Funcionario funcionario, Cliente cliente, Especialidade especialidade, LocalDateTime data) {
        var agendamento = new Agendamento(funcionario, cliente, especialidade, data);
        em.persist(agendamento);
    }

    private Especialidade cadastrarEspecialidade(String nome, String descricao) {
        var dados = new dtoEspecialidadeCadastrar("Corte", new BigDecimal("35.00"), "Corte degradê");
        var especialidade = new Especialidade(dados);
        em.persist(especialidade);
        return especialidade;
    }

    private Cliente cadastrarCliente(String nome, String email, String senha, String telefone, String cpf) {
        var endereco = dadosEndereco();

        var dadosCliente = new dtoClienteCadastrar(
                nome,
                email,
                senha,
                telefone,
                cpf,
                endereco,
                UsuarioRole.CLIENTE
        );

        var cliente = new Cliente(dadosCliente);
        em.persist(cliente);
        return cliente;
    }

    private Funcionario cadastrarFuncionario(String nome, String email, String cpf, Especialidade especialidade) {
        var dados = new dtoFuncionarioCadastrar(
                nome,
                email,
                "11999999999",
                cpf,
                dadosEndereco(),
                List.of(especialidade.getId()),
                UsuarioRole.FUNCIONARIO
        );
        var funcionario = new Funcionario(dados);
        funcionario.getEspecialidades().add(especialidade);
        em.persist(funcionario);
        return funcionario;
    }

    private dtoEndereco dadosEndereco() {
        return new dtoEndereco(
                "Rua Teste",
                "Bairro",
                "00000000",
                "Cidade",
                "UF",
                null,
                null
        );
    }
}
package erp.Salao.domain.funcionario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import erp.Salao.domain.endereco.Endereco;
import erp.Salao.domain.especialidade.Especialidade;
import erp.Salao.domain.funcionario.dto.dtoFuncionarioAtualizar;
import erp.Salao.domain.funcionario.dto.dtoFuncionarioCadastrar;
import erp.Salao.domain.usuario.Usuario;

@Table(name = "funcionarios")
@Entity(name = "funcionarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "funcionario_especialidade",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private Set<Especialidade> especialidades = new HashSet<>();

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Funcionario(dtoFuncionarioCadastrar dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.especialidades = new HashSet<>();
    }

    public void atualizarEspecialidades(List<Especialidade> novasEspecialidades) {
        if (novasEspecialidades != null) {
            novasEspecialidades.forEach(nova -> {
                if (!this.especialidades.contains(nova)) {
                    this.especialidades.add(nova);
                }
            });
        }
    }

    public void removerEspecialidades(List<Especialidade> antigasEspecialidades) {
        if (antigasEspecialidades != null) {
            antigasEspecialidades.forEach(especialidade -> {
                this.especialidades.remove(especialidade);
            });
        }
    }

    public void atualizarFuncionario(@Valid dtoFuncionarioAtualizar dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void excluirFuncionario() {
        this.ativo = false;
    }

    public void reativarFuncionario() {
        this.ativo = true;
    }
}


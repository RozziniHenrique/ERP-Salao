package uscs.STEFER.domain.especialidade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeAtualizar;
import uscs.STEFER.domain.especialidade.dto.dtoEspecialidadeCadastrar;
import uscs.STEFER.domain.funcionario.Funcionario;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table(name = "especialidades")
@Entity(name = "Especialidade")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    @Positive
    private BigDecimal preco;

    private Boolean ativo;

    @ManyToMany(mappedBy = "especialidades")
    @JsonIgnore
    private Set<Funcionario> funcionarios = new HashSet<>();

    public Especialidade(dtoEspecialidadeCadastrar dados) {
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.descricao = dados.descricao();
        this.ativo = true;
    }

    public void atualizar(@Valid dtoEspecialidadeAtualizar dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.preco() != null) {
            this.preco = dados.preco();
        }
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }

    }

    public void excluir() {
        this.ativo = false;
    }

    public void reativar() {
        this.ativo = true;
    }
}

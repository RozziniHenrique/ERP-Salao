package uscs.STEFER.domain.agendamento;

import jakarta.persistence.*;
import lombok.*;
import uscs.STEFER.domain.cliente.Cliente;
import uscs.STEFER.domain.especialidade.Especialidade;
import uscs.STEFER.domain.funcionario.Funcionario;

import java.time.LocalDateTime;

@Table(name = "agendamentos")
@Entity(name = "Agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;

    private LocalDateTime data;

    private String motivoCancelamento;

    private Integer nota;

    private Boolean concluido = false;

    public Agendamento(Funcionario funcionario, Cliente cliente, Especialidade especialidade, LocalDateTime data) {
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.especialidade = especialidade;
        this.data = data;
        this.concluido = false;
        this.motivoCancelamento = null;
    }

    public void finalizar(Integer nota) {
        this.concluido = true;
        this.nota = nota;
    }
}
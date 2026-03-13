package erp.Salao.domain.agendamento;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import erp.Salao.domain.cliente.Cliente;
import erp.Salao.domain.especialidade.Especialidade;
import erp.Salao.domain.funcionario.Funcionario;

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
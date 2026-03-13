package erp.Salao.domain.avaliacao;

import erp.Salao.domain.agendamento.Agendamento;
import erp.Salao.domain.avaliacao.dto.dtoAvaliacaoCadastrar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Avaliacao")
@Table(name = "avaliacoes")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;
    private String comentario;

    @OneToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    public Avaliacao(dtoAvaliacaoCadastrar dados, Agendamento agendamento) {
        this.nota = dados.nota();
        this.comentario = dados.comentario();
        this.agendamento = agendamento;
    }
}

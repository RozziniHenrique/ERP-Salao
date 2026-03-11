import React from "react";
import { styles } from "../Agendamento.styles";

export default function AgendamentoForm({
  novoAgendamento,
  setNovoAgendamento,
  onSubmit,
  clientes,
  funcionarios,
  especialidades,
}) {
  const funcionariosFiltrados = funcionarios.filter((f) => {
    if (!novoAgendamento.idEspecialidade) return true;
    return f.especialidades?.some(
      (esp) => String(esp.id) === String(novoAgendamento.idEspecialidade),
    );
  });

  return (
    <div style={styles.card}>
      <h3 style={styles.cardTitle}>Preencha os detalhes</h3>
      <form onSubmit={onSubmit} style={styles.formAgendamento}>
        <div style={styles.inputGroup}>
          <label style={styles.label}>Cliente</label>
          <select
            required
            style={styles.input}
            value={novoAgendamento.idCliente}
            onChange={(e) =>
              setNovoAgendamento({
                ...novoAgendamento,
                idCliente: e.target.value,
              })
            }
          >
            <option value="">Selecione o cliente...</option>
            {clientes.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nome}
              </option>
            ))}
          </select>
        </div>

        <div style={styles.inputGroup}>
          <label style={styles.label}>Serviço (Especialidade)</label>
          <select
            required
            style={styles.input}
            value={novoAgendamento.idEspecialidade}
            onChange={(e) =>
              setNovoAgendamento({
                ...novoAgendamento,
                idEspecialidade: e.target.value,
                idFuncionario: "",
              })
            }
          >
            <option value="">Selecione o serviço...</option>
            {especialidades.map((e) => (
              <option key={e.id} value={e.id}>
                {e.nome}
              </option>
            ))}
          </select>
        </div>

        <div style={styles.inputGroup}>
          <label style={styles.label}>Profissional</label>
          <select
            required
            style={styles.input}
            value={novoAgendamento.idFuncionario}
            onChange={(e) =>
              setNovoAgendamento({
                ...novoAgendamento,
                idFuncionario: e.target.value,
              })
            }
          >
            <option value="">Selecione o profissional...</option>
            {funcionariosFiltrados.map((f) => (
              <option key={f.id} value={f.id}>
                {f.nome}
              </option>
            ))}
          </select>
        </div>

        <div style={styles.inputGroup}>
          <label style={styles.label}>Data e Hora</label>
          <input
            type="datetime-local"
            value={novoAgendamento.data}
            onChange={(e) =>
              setNovoAgendamento({ ...novoAgendamento, data: e.target.value })
            }
            style={styles.input}
            required
          />
        </div>

        <button type="submit" style={styles.btnAgendar}>
          CONFIRMAR AGENDAMENTO
        </button>
      </form>
    </div>
  );
}

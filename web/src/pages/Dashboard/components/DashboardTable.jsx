import React from "react";
import { formatarHora } from "@/utils/formatters";
import { styles } from "../Dashboard.styles";

const StatusOuAcoes = ({ ag, onFinalizar, onCancelar }) => {
  if (ag.concluido) return <span style={styles.badgeSuccess}>CONCLUÍDO</span>;
  if (ag.motivoCancelamento)
    return <span style={styles.badgeCancel}>CANCELADO</span>;
  return (
    <div style={styles.actionsContainer}>
      <button onClick={() => onFinalizar(ag.id)} style={styles.btnFinalizar}>
        OK
      </button>
      <button onClick={() => onCancelar(ag.id)} style={styles.btnCancelar}>
        X
      </button>
    </div>
  );
};

export default function DashboardTable({
  agendamentos,
  onFinalizar,
  onCancelar,
}) {
  return (
    <div style={styles.tableCard}>
      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.th}>HORA</th>
            <th style={styles.th}>CLIENTE</th>
            <th style={styles.th}>PROFISSIONAL</th>
            <th style={styles.th}>SERVIÇO</th>
            <th style={{ ...styles.th, textAlign: "right" }}>AÇÕES</th>
          </tr>
        </thead>
        <tbody>
          {agendamentos.length === 0 ? (
            <tr>
              <td colSpan="4" style={styles.emptyRow}>
                Nenhum agendamento encontrado.
              </td>
            </tr>
          ) : (
            agendamentos.map((ag) => (
              <tr key={ag.id}>
                <td style={styles.td}>{formatarHora(ag.data)}</td>
                <td style={styles.td}>{ag.cliente?.nome || ag.nomeCliente}</td>
                <td style={styles.td}>
                  {ag.nomeFuncionario || "Não atribuído"}
                </td>
                <td style={styles.td}>
                  {ag.especialidade?.nome || ag.especialidade}
                </td>
                <td style={{ ...styles.td, textAlign: "right" }}>
                  <StatusOuAcoes
                    ag={ag}
                    onFinalizar={onFinalizar}
                    onCancelar={onCancelar}
                  />
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

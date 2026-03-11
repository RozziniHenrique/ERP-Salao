import React from "react";
import { styles } from "../Servicos.styles";

export default function ServicoStats({ totalAtivos, ticketMedio }) {
  return (
    <div style={styles.statsRow}>
      <div style={styles.miniCard}>
        <span style={styles.labelFaint}>Especialidades Ativas</span>
        <p style={styles.statNumber}>{totalAtivos}</p>
      </div>
      <div style={styles.miniCard}>
        <span style={styles.labelFaint}>Ticket Médio</span>
        <p style={styles.statNumber}>
          {new Intl.NumberFormat("pt-BR", {
            style: "currency",
            currency: "BRL",
          }).format(ticketMedio)}
        </p>
      </div>
    </div>
  );
}

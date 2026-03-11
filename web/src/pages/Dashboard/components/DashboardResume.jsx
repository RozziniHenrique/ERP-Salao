import React from "react";
import { styles } from "../Dashboard.styles";
import { formatarMoeda } from "@/utils/formatters";

export default function DashboardResume({ agendamentos, faturamento }) {
  const concluidos = agendamentos.filter((a) => a.concluido).length;
  const cancelados = agendamentos.filter((a) => a.motivoCancelamento).length;
  const pendentes = agendamentos.length - concluidos - cancelados;

  return (
    <div style={styles.statsGrid}>
      <StatCard title="Agendados" value={agendamentos.length} color="#2B3674" />
      <StatCard title="Concluídos" value={concluidos} color="#05CD99" />
      <StatCard
        title="Receita Dia"
        value={formatarMoeda(faturamento)}
        isMoney
        color="#2E7D32"
      />
      <StatCard title="Pendentes" value={pendentes} color="#FFB800" />
    </div>
  );
}

const StatCard = ({ title, value, color }) => (
  <div style={{ ...styles.statCard, borderLeft: `5px solid ${color}` }}>
    <span style={styles.statTitle}>{title}</span>
    <p style={{ ...styles.statNumber, color: color }}>{value}</p>
  </div>
);

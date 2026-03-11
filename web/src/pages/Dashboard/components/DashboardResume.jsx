import React from "react";
import { formatarMoeda } from "@/utils/formatters";
import { styles } from "../Dashboard.styles";

const StatCard = ({ title, value, isMoney }) => (
  <div style={styles.statCard}>
    <span style={styles.statTitle}>{title}</span>
    <p style={{ ...styles.statNumber, color: isMoney ? "#2E7D32" : "#2B3674" }}>
      {value}
    </p>
  </div>
);

export default function DashboardResume({ totalAgendamentos, faturamento }) {
  return (
    <div style={styles.statsGrid}>
      <StatCard title="Hoje" value={totalAgendamentos} />
      <StatCard title="Receita" value={formatarMoeda(faturamento)} isMoney />
    </div>
  );
}

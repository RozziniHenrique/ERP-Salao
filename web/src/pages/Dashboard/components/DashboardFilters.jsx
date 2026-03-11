import React from "react";
import { styles } from "../Dashboard.styles";

export default function DashboardFilters({
  filtros,
  setFiltros,
  especialidades,
}) {
  return (
    <div style={styles.filterBar}>
      <input
        type="date"
        style={styles.input}
        value={filtros.data}
        onChange={(e) => setFiltros({ ...filtros, data: e.target.value })}
      />

      <select
        style={styles.input}
        value={filtros.especialidade}
        onChange={(e) =>
          setFiltros({ ...filtros, especialidade: e.target.value })
        }
      >
        <option value="">Todos os Serviços</option>
        {especialidades.map((e) => (
          <option key={e.id} value={e.id}>
            {e.nome}
          </option>
        ))}
      </select>
    </div>
  );
}

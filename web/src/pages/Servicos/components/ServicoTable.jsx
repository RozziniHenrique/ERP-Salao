import React from "react";
import { styles } from "../Servicos.styles";

export default function ServicoTable({
  servicos,
  busca,
  setBusca,
  verInativos,
  setVerInativos,
  onEdit,
  onDesativar,
  onReativar,
  loading,
}) {
  return (
    <section style={styles.card}>
      <div style={styles.headerAcoes}>
        <input
          placeholder="🔍 Buscar especialidade..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          style={styles.inputBusca}
        />
        <button
          onClick={() => setVerInativos(!verInativos)}
          style={{
            ...styles.btnToggle,
            backgroundColor: verInativos ? "#4A148C" : "#F4F7FE",
            color: verInativos ? "#FFF" : "#4A148C",
            border: "1px solid #4A148C",
          }}
        >
          {verInativos ? "👁️ Ver Ativos" : "📁 Ver Inativos"}
        </button>
      </div>

      {loading ? (
        <p style={{ textAlign: "center", color: "#A3AED0" }}>Carregando...</p>
      ) : (
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>ESPECIALIDADE</th>
              <th style={styles.th}>PREÇO UNITÁRIO</th>
              <th style={{ ...styles.th, textAlign: "right" }}>AÇÕES</th>
            </tr>
          </thead>
          <tbody>
            {servicos.length === 0 ? (
              <tr>
                <td
                  colSpan="3"
                  style={{
                    textAlign: "center",
                    padding: "20px",
                    color: "#A3AED0",
                  }}
                >
                  Nenhum serviço encontrado.
                </td>
              </tr>
            ) : (
              servicos.map((s) => (
                <tr key={s.id}>
                  <td style={styles.td}>
                    <strong>{s.nome}</strong>
                  </td>
                  <td
                    style={{
                      ...styles.td,
                      color: "#4A148C",
                      fontWeight: "700",
                    }}
                  >
                    {new Intl.NumberFormat("pt-BR", {
                      style: "currency",
                      currency: "BRL",
                    }).format(s.preco || 0)}
                  </td>
                  <td style={{ ...styles.td, textAlign: "right" }}>
                    <button onClick={() => onEdit(s)} style={styles.btnEdit}>
                      Editar
                    </button>
                    {s.ativo !== false ? (
                      <button
                        onClick={() => onDesativar(s.id)}
                        style={styles.btnDelete}
                      >
                        Desativar
                      </button>
                    ) : (
                      <button
                        onClick={() => onReativar(s.id)}
                        style={styles.btnSuccess}
                      >
                        Reativar
                      </button>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </section>
  );
}

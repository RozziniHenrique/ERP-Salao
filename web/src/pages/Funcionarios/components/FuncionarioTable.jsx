import React from "react";
import * as S from "../Funcionarios.styles";

export default function FuncionarioTable({
  funcionarios,
  busca,
  setBusca,
  verInativos,
  setVerInativos,
  onRemoverEsp,
  onEdit,
  onDesativar,
  onReativar,
  loading,
}) {
  return (
    <section style={S.styles.card}>
      <div style={S.styles.headerAcoes}>
        <input
          placeholder="🔍 Buscar profissional..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          style={S.styles.inputBusca}
        />
        <button
          onClick={() => setVerInativos(!verInativos)}
          style={{
            ...S.styles.btnToggle,
            backgroundColor: verInativos ? "#4A148C" : "#F4F7FE",
            color: verInativos ? "#FFF" : "#4A148C",
            border: "1px solid #4A148C",
          }}
        >
          {verInativos ? "👁️ Ver Ativos" : "📁 Ver Inativos"}
        </button>
      </div>

      {loading ? (
        <p style={{ textAlign: "center", color: "#A3AED0", padding: "20px" }}>
          Carregando equipe...
        </p>
      ) : (
        <table style={S.styles.table}>
          <thead>
            <tr>
              <th style={S.styles.th}>PROFISSIONAL</th>
              <th style={S.styles.th}>CONTATO</th>
              <th style={S.styles.th}>ESPECIALIDADES</th>
              <th style={{ ...S.styles.th, textAlign: "right" }}>AÇÕES</th>
            </tr>
          </thead>
          <tbody>
            {funcionarios.length === 0 ? (
              <tr>
                <td
                  colSpan="4"
                  style={{
                    textAlign: "center",
                    padding: "30px",
                    color: "#A3AED0",
                  }}
                >
                  Nenhum profissional encontrado.
                </td>
              </tr>
            ) : (
              funcionarios.map((f) => (
                <tr key={f.id}>
                  <td style={S.styles.td}>
                    <strong style={{ display: "block", color: "#2B3674" }}>
                      {f.nome}
                    </strong>
                  </td>
                  <td style={S.styles.td}>
                    <div style={{ fontSize: "0.85rem" }}>
                      {f.email} <br />
                      <span style={{ color: "#A3AED0" }}>{f.telefone}</span>
                    </div>
                  </td>
                  <td style={S.styles.td}>
                    <div
                      style={{
                        display: "flex",
                        flexWrap: "wrap",
                        gap: "6px",
                        alignItems: "center",
                      }}
                    >
                      {f.especialidades?.map((esp) => (
                        <span
                          key={esp.id}
                          style={{
                            fontSize: "0.7rem",
                            backgroundColor: "#F4F7FE",
                            padding: "2px 8px",
                            borderRadius: "4px",
                            color: "#4A148C",
                            border: "1px solid #E0E5F2",
                            display: "flex",
                            alignItems: "center",
                            gap: "5px",
                          }}
                        >
                          {esp.nome}
                          <button
                            onClick={(e) => {
                              e.stopPropagation();
                              onRemoverEsp(f, esp.id);
                            }}
                            style={{
                              border: "none",
                              background: "none",
                              color: "#FF4D4F",
                              cursor: "pointer",
                              fontWeight: "bold",
                              fontSize: "10px",
                              padding: "0 2px",
                            }}
                          >
                            ✕
                          </button>
                        </span>
                      ))}
                      <button
                        onClick={() => {
                          onEdit(f);
                          window.scrollTo({ top: 0, behavior: "smooth" });
                        }}
                        title="Adicionar/Editar Especialidades"
                        style={{
                          border: "1px dashed #4A148C",
                          background: "#F3E5F5",
                          borderRadius: "50%",
                          width: "22px",
                          height: "22px",
                          cursor: "pointer",
                          color: "#4A148C",
                          fontSize: "14px",
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center",
                          fontWeight: "bold",
                        }}
                      >
                        +
                      </button>
                    </div>
                  </td>
                  <td style={{ ...S.styles.td, textAlign: "right" }}>
                    <button onClick={() => onEdit(f)} style={S.styles.btnEdit}>
                      Editar
                    </button>
                    {f.ativo !== false ? (
                      <button
                        onClick={() => onDesativar(f.id)}
                        style={S.styles.btnDelete}
                      >
                        Desativar
                      </button>
                    ) : (
                      <button
                        onClick={() => onReativar(f.id)}
                        style={S.styles.btnSuccess}
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

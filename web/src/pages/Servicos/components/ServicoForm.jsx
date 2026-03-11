import React from "react";
import { styles } from "../Servicos.styles";

export default function ServicoForm({
  novoServico,
  setNovoServico,
  onSubmit,
  editandoId,
  onCancelar,
}) {
  return (
    <section
      style={{
        ...styles.card,
        border: editandoId ? "2px solid #4A148C" : "none",
      }}
    >
      <h3 style={styles.cardTitle}>
        {editandoId
          ? `📝 Editando: ${novoServico.nome}`
          : "✨ Nova Especialidade"}
      </h3>
      <form onSubmit={onSubmit} style={styles.formGrid}>
        <div style={styles.inputGroup}>
          <label style={styles.labelInput}>Nome</label>
          <input
            placeholder="Ex: Corte de Cabelo"
            value={novoServico.nome}
            onChange={(e) =>
              setNovoServico({ ...novoServico, nome: e.target.value })
            }
            style={styles.input}
            required
          />
        </div>
        <div style={styles.inputGroup}>
          <label style={styles.labelInput}>Preço (R$)</label>
          <input
            placeholder="0,00"
            type="number"
            step="0.01"
            value={novoServico.preco}
            onChange={(e) =>
              setNovoServico({ ...novoServico, preco: e.target.value })
            }
            style={styles.input}
            required
          />
        </div>
        <div style={styles.btnGroup}>
          <button type="submit" style={styles.btnPrimary}>
            {editandoId ? "SALVAR" : "CADASTRAR"}
          </button>
          {editandoId && (
            <button type="button" onClick={onCancelar} style={styles.btnCancel}>
              CANCELAR
            </button>
          )}
        </div>
      </form>
    </section>
  );
}

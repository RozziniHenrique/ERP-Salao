import React from "react";
import * as S from "../Funcionarios.styles";
import { maskCPF, maskPhone, maskCEP } from "@/utils/masks";

export default function FuncionarioForm({
  novoFunc,
  setNovoFunc,
  onSubmit,
  editandoId,
  onCancelar,
  especialidades,
  buscarCep,
}) {
  const updateEndereco = (campo, valor) => {
    setNovoFunc({
      ...novoFunc,
      endereco: {
        ...(novoFunc.endereco || {}),
        [campo]: valor,
      },
    });
  };

  // Função auxiliar para lidar com a seleção de especialidades
  const handleToggleEspecialidade = (id) => {
    const idStr = String(id);
    const listaAtual = novoFunc.especialidadesIds || [];

    const novas = listaAtual.includes(idStr)
      ? listaAtual.filter((i) => i !== idStr)
      : [...listaAtual, idStr];

    setNovoFunc({ ...novoFunc, especialidadesIds: novas });
  };

  return (
    <section
      style={{
        ...S.styles.card,
        border: editandoId ? "2px solid #4A148C" : "none",
      }}
    >
      <h3 style={S.styles.cardTitle}>
        {editandoId ? `✏️ Editando: ${novoFunc.nome}` : "🆕 Novo Profissional"}
      </h3>

      <form onSubmit={onSubmit}>
        <div style={S.styles.formGrid}>
          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>Nome Completo</label>
            <input
              placeholder="Ex: João Silva"
              value={novoFunc.nome || ""}
              onChange={(e) =>
                setNovoFunc({ ...novoFunc, nome: e.target.value })
              }
              style={S.styles.input}
              required
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>E-mail</label>
            <input
              placeholder="email@exemplo.com"
              type="email"
              value={novoFunc.email || ""}
              onChange={(e) =>
                setNovoFunc({ ...novoFunc, email: e.target.value })
              }
              style={{
                ...S.styles.input,
                backgroundColor: editandoId ? "#f0f0f0" : "#F4F7FE",
              }}
              disabled={!!editandoId}
              required
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>Telefone</label>
            <input
              placeholder="(00) 00000-0000"
              value={novoFunc.telefone || ""}
              onChange={(e) =>
                setNovoFunc({
                  ...novoFunc,
                  telefone: maskPhone(e.target.value),
                })
              }
              style={S.styles.input}
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>CPF</label>
            <input
              placeholder="000.000.000-00"
              value={novoFunc.cpf || ""}
              onChange={(e) =>
                setNovoFunc({ ...novoFunc, cpf: maskCPF(e.target.value) })
              }
              style={{
                ...S.styles.input,
                backgroundColor: editandoId ? "#f0f0f0" : "#F4F7FE",
              }}
              disabled={!!editandoId}
              required
            />
          </div>
        </div>

        <div style={S.styles.formGridEndereco}>
          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>CEP</label>
            <input
              placeholder="00000-000"
              value={novoFunc.endereco?.cep || ""}
              onChange={(e) => {
                const val = maskCEP(e.target.value);
                updateEndereco("cep", val);
                if (val.replace(/\D/g, "").length === 8) buscarCep(val);
              }}
              style={S.styles.input}
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>Rua</label>
            <input
              placeholder="Logradouro"
              value={novoFunc.endereco?.logradouro || ""}
              onChange={(e) => updateEndereco("logradouro", e.target.value)}
              style={S.styles.input}
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>Nº</label>
            <input
              placeholder="Nº"
              value={novoFunc.endereco?.numero || ""}
              onChange={(e) => updateEndereco("numero", e.target.value)}
              style={S.styles.input}
            />
          </div>

          <div style={S.styles.inputGroup}>
            <label style={S.styles.labelInput}>Cidade/UF</label>
            <input
              placeholder="Cidade - UF"
              value={`${novoFunc.endereco?.cidade || ""} ${novoFunc.endereco?.uf || ""}`}
              disabled
              style={{ ...S.styles.input, backgroundColor: "#f0f0f0" }}
            />
          </div>
        </div>

        <div style={{ marginTop: "20px" }}>
          <label style={S.styles.labelInput}>Especialidades Atendidas:</label>
          <div style={S.styles.checkboxGrid}>
            {especialidades
              .filter((e) => e.ativo !== false)
              .map((esp) => {
                const idStr = String(esp.id);
                const selecionado = (novoFunc.especialidadesIds || []).includes(
                  idStr,
                );

                return (
                  <label
                    key={esp.id}
                    style={{
                      display: "flex",
                      alignItems: "center",
                      gap: "8px",
                      padding: "8px 12px",
                      backgroundColor: selecionado ? "#F3E5F5" : "#F4F7FE",
                      borderRadius: "8px",
                      cursor: "pointer",
                      border: selecionado
                        ? "1px solid #4A148C"
                        : "1px solid #E0E5F2",
                      fontSize: "0.85rem",
                      color: "#4A148C",
                      fontWeight: "600",
                      transition: "0.2s",
                    }}
                  >
                    <input
                      type="checkbox"
                      checked={selecionado}
                      onChange={() => handleToggleEspecialidade(esp.id)}
                    />
                    {esp.nome}
                  </label>
                );
              })}
          </div>
        </div>

        <div style={{ ...S.styles.btnGroup, marginTop: "25px" }}>
          <button type="submit" style={S.styles.btnPrimary}>
            {editandoId ? "SALVAR ALTERAÇÕES" : "CADASTRAR PROFISSIONAL"}
          </button>
          {editandoId && (
            <button
              type="button"
              onClick={onCancelar}
              style={S.styles.btnCancel}
            >
              CANCELAR
            </button>
          )}
        </div>
      </form>
    </section>
  );
}

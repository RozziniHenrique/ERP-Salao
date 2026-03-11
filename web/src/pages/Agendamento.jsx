import React, { useState, useEffect } from "react";
import api from "../services/api.js";
import Layout from "../components/layout.jsx";

export default function Agendamento() {
  const [clientes, setClientes] = useState([]);
  const [funcionarios, setFuncionarios] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [novoAgendamento, setNovoAgendamento] = useState({
    idCliente: "",
    idFuncionario: "",
    idEspecialidade: "",
    data: "",
  });

  const carregarDados = async () => {
    try {
      const [resCli, resFunc, resEsp] = await Promise.all([
        api.get("/clientes"),
        api.get("/funcionarios"),
        api.get("/especialidades"),
      ]);
      setClientes(
        (resCli.data.content || resCli.data || []).filter((i) => i.ativo),
      );
      setFuncionarios(
        (resFunc.data.content || resFunc.data || []).filter((i) => i.ativo),
      );
      setEspecialidades(
        (resEsp.data.content || resEsp.data || []).filter((i) => i.ativo),
      );
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleAgendar = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        idCliente: Number(novoAgendamento.idCliente),
        idFuncionario: Number(novoAgendamento.idFuncionario),
        idEspecialidade: Number(novoAgendamento.idEspecialidade),
        data:
          novoAgendamento.data.replace("T", " ") +
          (novoAgendamento.data.length === 16 ? ":00" : ""),
      };
      await api.post("/agendamentos", payload);
      alert("✅ Agendamento realizado!");
      setNovoAgendamento({
        idCliente: "",
        idFuncionario: "",
        idEspecialidade: "",
        data: "",
      });
    } catch (err) {
      alert("Erro ao agendar.");
    }
  };

  return (
    <Layout titulo="Novo Agendamento">
      <div style={styles.card}>
        <h3 style={styles.cardTitle}>Preencha os detalhes</h3>
        <form onSubmit={handleAgendar} style={styles.formAgendamento}>
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
              <option value="">Selecione...</option>
              {clientes.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.nome}
                </option>
              ))}
            </select>
          </div>
          <div style={styles.inputGroup}>
            <label style={styles.label}>Serviço</label>
            <select
              required
              style={styles.input}
              value={novoAgendamento.idEspecialidade}
              onChange={(e) =>
                setNovoAgendamento({
                  ...novoAgendamento,
                  idEspecialidade: e.target.value,
                })
              }
            >
              <option value="">Selecione...</option>
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
              <option value="">Selecione...</option>
              {funcionarios.map((f) => (
                <option key={f.id} value={f.id}>
                  {f.nome}
                </option>
              ))}
            </select>
          </div>
          <div style={styles.inputGroup}>
            <label style={styles.label}>Data e Hora</label>
            <input
              type="time"
              step="3600"
              value={novoAgendamento.hora}
              onChange={(e) =>
                setNovoAgendamento({ ...novoAgendamento, hora: e.target.value })
              }
              style={styles.input}
              required
            />
          </div>
          <button type="submit" style={styles.btnAgendar}>
            CONFIRMAR
          </button>
        </form>
      </div>
    </Layout>
  );
}

const styles = {
  card: {
    backgroundColor: "#FFF",
    padding: "30px",
    borderRadius: "20px",
    boxShadow: "0 10px 30px rgba(0,0,0,0.05)",
    maxWidth: "800px",
    margin: "0 auto",
  },
  cardTitle: {
    color: "#4A148C",
    marginBottom: "25px",
    fontWeight: "800",
    textAlign: "center",
  },
  formAgendamento: {
    display: "grid",
    gridTemplateColumns: "1fr 1fr",
    gap: "20px",
  },
  inputGroup: { display: "flex", flexDirection: "column", gap: "8px" },
  label: { fontSize: "0.85rem", color: "#A3AED0", fontWeight: "bold" },
  input: {
    padding: "12px",
    borderRadius: "10px",
    border: "1px solid #E0E5F2",
    backgroundColor: "#F4F7FE",
    color: "#2B3674",
  },
  btnAgendar: {
    gridColumn: "1 / -1",
    backgroundColor: "#4A148C",
    color: "#FFF",
    border: "none",
    padding: "15px",
    borderRadius: "12px",
    fontWeight: "bold",
    cursor: "pointer",
    marginTop: "10px",
  },
};

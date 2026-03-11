import React, { useState, useEffect } from "react";
import api from "../services/api";
import Layout from "../components/layout";

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function carregarClientes() {
      try {
        const res = await api.get("/clientes");
        setClientes(res.data.content || res.data || []);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    }
    carregarClientes();
  }, []);

  return (
    <Layout titulo="👥 Clientes Cadastrados">
      <div style={styles.card}>
        {loading ? (
          <p style={{ color: "#2B3674" }}>Carregando...</p>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>NOME</th>
                <th style={styles.th}>CPF</th>
                <th style={styles.th}>E-MAIL</th>
                <th style={styles.th}>STATUS</th>
              </tr>
            </thead>
            <tbody>
              {clientes.map((c) => (
                <tr key={c.id}>
                  <td style={styles.td}>{c.nome}</td>
                  <td style={styles.td}>{c.cpf}</td>
                  <td style={styles.td}>{c.email}</td>
                  <td style={styles.td}>
                    <span style={styles.badge(c.ativo !== false)}>
                      {c.ativo !== false ? "ATIVO" : "INATIVO"}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </Layout>
  );
}

const styles = {
  card: {
    backgroundColor: "#FFF",
    padding: "25px",
    borderRadius: "15px",
    boxShadow: "0 10px 20px rgba(0,0,0,0.05)",
    border: "1px solid #E0E5F2",
  },
  table: { width: "100%", borderCollapse: "collapse" },
  th: {
    textAlign: "left",
    padding: "15px",
    color: "#A3AED0",
    fontSize: "0.8rem",
    fontWeight: "800",
    borderBottom: "2px solid #F4F7FE",
  },
  td: {
    padding: "15px",
    color: "#2B3674",
    fontSize: "0.95rem",
    borderBottom: "1px solid #F4F7FE",
    fontWeight: "500",
  },
  badge: (ativo) => ({
    padding: "5px 12px",
    borderRadius: "20px",
    fontSize: "0.7rem",
    fontWeight: "bold",
    backgroundColor: ativo ? "#E8F5E9" : "#FFEBEE",
    color: ativo ? "#2E7D32" : "#D32F2F",
  }),
};

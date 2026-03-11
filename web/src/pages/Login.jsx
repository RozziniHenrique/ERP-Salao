import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  async function handleLogin(e) {
    e.preventDefault();
    try {
      const response = await api.post("/login", { login: email, senha: senha });
      localStorage.setItem("user", JSON.stringify(response.data));
      navigate("/dashboard");
    } catch (err) {
      alert("Acesso negado. Verifique suas credenciais.");
    }
  }

  return (
    <div style={loginStyles.container}>
      <form onSubmit={handleLogin} style={loginStyles.card}>
        <div style={loginStyles.logoArea}>
          <h1 style={loginStyles.logoText}>STEFER 💜</h1>
          <p style={loginStyles.subtitle}>Acesse a gestão Embelleze</p>
        </div>

        <div style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
          <input
            type="email"
            placeholder="E-mail"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={loginStyles.input}
          />
          <input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
            style={loginStyles.input}
          />
          <button type="submit" style={loginStyles.button}>
            ENTRAR NO SISTEMA
          </button>
        </div>
      </form>
    </div>
  );
}

const loginStyles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F4F7FE",
  },
  card: {
    backgroundColor: "#FFF",
    padding: "50px",
    borderRadius: "20px",
    width: "100%",
    maxWidth: "400px",
    boxShadow: "0 20px 40px rgba(74, 20, 140, 0.1)",
    textAlign: "center",
  },
  logoArea: { marginBottom: "35px" },
  logoText: {
    color: "#4A148C",
    fontSize: "2.2rem",
    fontWeight: "900",
    letterSpacing: "2px",
  },
  subtitle: { color: "#707EAE", fontSize: "0.9rem", marginTop: "5px" },
  input: {
    width: "100%",
    padding: "15px",
    borderRadius: "12px",
    border: "1px solid #E0E5F2",
    outline: "none",
    boxSizing: "border-box",
    backgroundColor: "#F4F7FE",
  },
  button: {
    width: "100%",
    padding: "15px",
    backgroundColor: "#4A148C",
    color: "#FFF",
    border: "none",
    borderRadius: "12px",
    cursor: "pointer",
    fontWeight: "bold",
    fontSize: "1rem",
    transition: "0.3s",
  },
};

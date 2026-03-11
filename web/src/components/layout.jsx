import React from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

export default function Layout({ children, titulo }) {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const isActive = (path) => location.pathname === path;

  return (
    <div style={styles.container}>
      <aside style={styles.sidebar}>
        <div style={styles.logoArea}>
          <h2 style={styles.logoText}>STEFER</h2>
          <p style={styles.logoSub}>System v1.0</p>
        </div>

        <nav style={styles.nav}>
          <Link
            to="/dashboard"
            style={isActive("/dashboard") ? styles.linkActive : styles.link}
          >
            📊 Dashboard
          </Link>
          <Link
            to="/agendamentos"
            style={isActive("/agendamentos") ? styles.linkActive : styles.link}
          >
            📅 Agendamentos
          </Link>
          <Link
            to="/clientes"
            style={isActive("/clientes") ? styles.linkActive : styles.link}
          >
            👥 Clientes
          </Link>
          <Link
            to="/servicos"
            style={isActive("/servicos") ? styles.linkActive : styles.link}
          >
            🛠️ Serviços (Espec.)
          </Link>
          <Link
            to="/funcionarios"
            style={isActive("/funcionarios") ? styles.linkActive : styles.link}
          >
            👔 Equipe
          </Link>
        </nav>

        <button onClick={handleLogout} style={styles.btnLogout}>
          🚪 Sair do Sistema
        </button>
      </aside>

      <main style={styles.mainContent}>
        <header style={styles.header}>
          <h1 style={styles.pageTitle}>{titulo}</h1>
          <div style={styles.userBadge}>Admin</div>
        </header>
        <div style={styles.pageBody}>{children}</div>
      </main>
    </div>
  );
}

const styles = {
  container: {
    display: "flex",
    minHeight: "100vh",
    backgroundColor: "#F4F7FE",
    fontFamily: "sans-serif",
  },
  sidebar: {
    width: "260px",
    backgroundColor: "#4A148C",
    color: "#FFF",
    display: "flex",
    flexDirection: "column",
    padding: "30px 0",
    position: "fixed",
    height: "100vh",
  },
  logoArea: { padding: "0 30px 40px 30px", textAlign: "center" },
  logoText: {
    margin: 0,
    fontSize: "1.8rem",
    letterSpacing: "2px",
    fontWeight: "800",
  },
  logoSub: { margin: 0, fontSize: "0.7rem", opacity: 0.6 },
  nav: { display: "flex", flexDirection: "column", flex: 1 },
  link: {
    padding: "15px 30px",
    color: "rgba(255,255,255,0.7)",
    textDecoration: "none",
    fontWeight: "600",
    fontSize: "0.95rem",
  },
  linkActive: {
    padding: "15px 30px",
    color: "#FFF",
    textDecoration: "none",
    fontWeight: "700",
    backgroundColor: "rgba(255,255,255,0.1)",
    borderLeft: "5px solid #FFF",
  },
  btnLogout: {
    margin: "20px 30px",
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid rgba(255,255,255,0.3)",
    backgroundColor: "transparent",
    color: "#FFF",
    cursor: "pointer",
    fontWeight: "bold",
  },
  mainContent: { flex: 1, marginLeft: "260px", padding: "40px" },
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "30px",
  },
  pageTitle: {
    color: "#2B3674",
    margin: 0,
    fontSize: "1.7rem",
    fontWeight: "700",
  },
  userBadge: {
    backgroundColor: "#FFF",
    padding: "8px 20px",
    borderRadius: "20px",
    fontWeight: "bold",
    color: "#4A148C",
    boxShadow: "0 4px 10px rgba(0,0,0,0.05)",
  },
  pageBody: { animation: "fadeIn 0.5s ease-in" },
};

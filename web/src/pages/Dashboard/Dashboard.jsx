import React, { useState, useEffect, useCallback } from "react";
import api from "../../services/api";
import Layout from "../../components/layout";
import DashboardResume from "./components/DashboardResume";
import DashboardFilters from "./components/DashboardFilters";
import DashboardTable from "./components/DashboardTable";
import DashboardChart from "./components/DashboardCharts";

export default function Dashboard() {
  const [agendamentos, setAgendamentos] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [resumo, setResumo] = useState({ faturamentoHoje: 0, totalGeral: 0 });
  const [loading, setLoading] = useState(true);

  const [filtros, setFiltros] = useState({
    data: new Date().toISOString().split("T")[0],
    especialidade: "",
  });

  const carregarDadosDashboard = useCallback(async () => {
    setLoading(true);
    try {
      const { data, especialidade } = filtros;

      const queryAgend = `/agendamentos?data=${data}${especialidade ? `&idEspecialidade=${especialidade}` : ""}`;
      const queryDash = `/dashboard?inicio=${data}&fim=${data}`;

      const [resAgend, resDash, resEsp] = await Promise.all([
        api.get(queryAgend),
        api.get(queryDash),
        api.get("/especialidades"),
      ]);

      setAgendamentos(resAgend.data.content || resAgend.data || []);
      setResumo(resDash.data || { faturamentoHoje: 0, totalGeral: 0 });
      setEspecialidades(resEsp.data.content || resEsp.data || []);
    } catch (err) {
      console.error("Erro ao sincronizar dashboard:", err);
    } finally {
      setLoading(false);
    }
  }, [filtros]);

  useEffect(() => {
    carregarDadosDashboard();
  }, [carregarDadosDashboard]);

  const handleFinalizar = async (id) => {
    if (!window.confirm("Deseja concluir este atendimento?")) return;

    try {
      await api.put("/agendamentos/finalizar", {
        id: Number(id),
        nota: 5,
      });
      alert("Atendimento finalizado com sucesso!");
      carregarDadosDashboard();
    } catch (err) {
      alert("Não foi possível finalizar o agendamento.");
    }
  };

  const handleCancelar = async (id) => {
    const motivo = prompt("Informe o motivo do cancelamento:");
    if (!motivo) return;

    try {
      await api.delete("/agendamentos", {
        data: { idAgendamento: Number(id), motivo },
      });
      alert("Agendamento cancelado.");
      carregarDadosDashboard();
    } catch (err) {
      console.error(err);
      alert("Erro ao cancelar agendamento.");
    }
  };

  return (
    <Layout titulo="Painel de Controle">
      <DashboardResume
        agendamentos={agendamentos}
        faturamento={resumo.faturamentoHoje}
      />

      <DashboardFilters
        filtros={filtros}
        setFiltros={setFiltros}
        especialidades={especialidades}
      />

      <div
        style={{
          display: "grid",
          gridTemplateColumns: agendamentos.length > 0 ? "1fr 2fr" : "1fr",
          gap: "20px",
          marginTop: "20px",
        }}
      >
        {agendamentos.length > 0 && (
          <DashboardChart agendamentos={agendamentos} />
        )}

        <DashboardTable
          agendamentos={agendamentos}
          onFinalizar={handleFinalizar}
          onCancelar={handleCancelar}
          loading={loading}
        />
      </div>
    </Layout>
  );
}

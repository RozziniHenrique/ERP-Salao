import React, { useState, useEffect, useCallback } from "react";
import api from "../../services/api";
import Layout from "../../components/layout";
import DashboardResume from "./components/DashboardResume";
import DashboardFilters from "./components/DashboardFilters";
import DashboardTable from "./components/DashboardTable";

export default function Dashboard() {
  // ESTADOS
  const [agendamentos, setAgendamentos] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [resumo, setResumo] = useState({ faturamentoHoje: 0, totalGeral: 0 });
  const [loading, setLoading] = useState(true);

  // Filtros concentrados
  const [filtros, setFiltros] = useState({
    data: new Date().toISOString().split("T")[0],
    especialidade: "",
  });

  // CARREGAMENTO DE DADOS
  const carregarDadosDashboard = useCallback(async () => {
    setLoading(true);
    try {
      const { data, especialidade } = filtros;

      // Montagem limpa das queries
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

  // AÇÕES DE GESTÃO
  const handleFinalizar = async (id) => {
    if (!window.confirm("Deseja concluir este atendimento?")) return;

    try {
      // Exemplo com nota fixa 5 conforme seu código, mas pronto para expansão
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
      // No DELETE com body, usamos a chave 'data' no Axios
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
      {/* Resumo visual de faturamento e volume */}
      <DashboardResume
        totalAgendamentos={agendamentos.length}
        faturamento={resumo.faturamentoHoje}
      />

      {/* Seção de busca e filtros de data/categoria */}
      <DashboardFilters
        filtros={filtros}
        setFiltros={setFiltros}
        especialidades={especialidades}
      />

      {/* Listagem principal */}
      <DashboardTable
        agendamentos={agendamentos}
        onFinalizar={handleFinalizar}
        onCancelar={handleCancelar}
        loading={loading}
      />
    </Layout>
  );
}

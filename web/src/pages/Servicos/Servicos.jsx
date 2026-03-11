import React, { useState, useEffect } from "react";
import api from "../../services/api";
import Layout from "../../components/layout";
import ServicoStats from "./components/ServicoStats";
import ServicoForm from "./components/ServicoForm";
import ServicoTable from "./components/ServicoTable";

export default function Servicos() {
  // ESTADOS
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(true);

  // Filtros
  const [busca, setBusca] = useState("");
  const [verInativos, setVerInativos] = useState(false);

  // Edição
  const [editandoId, setEditandoId] = useState(null);
  const estadoInicial = { nome: "", preco: "", descricao: "" };
  const [novoServico, setNovoServico] = useState(estadoInicial);

  // CARREGAMENTO
  const carregarServicos = async () => {
    try {
      setLoading(true);
      const res = await api.get(`/especialidades?ativos=${!verInativos}`);
      const lista = res.data.content || res.data || [];
      setServicos(lista);
    } catch (err) {
      console.error("Erro ao carregar serviços:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarServicos();
  }, [verInativos]);

  // FILTRAGEM (FRONT-END)
  const servicosFiltrados = servicos.filter((s) =>
    (s.nome || "").toLowerCase().includes(busca.toLowerCase()),
  );

  // CÁLCULOS PARA STATS
  const totalAtivos = servicos.filter((s) => s.ativo !== false).length;
  const ticketMedio =
    servicos.length > 0
      ? servicos.reduce((acc, s) => acc + (s.preco || 0), 0) / servicos.length
      : 0;

  // AÇÕES
  const handleSalvar = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...novoServico,
        preco: parseFloat(String(novoServico.preco).replace(",", ".")),
        ativo: true,
      };

      if (editandoId) {
        await api.put(`/especialidades`, { ...payload, id: editandoId });
        alert("Especialidade atualizada!");
      } else {
        await api.post("/especialidades", payload);
        alert("Especialidade cadastrada com sucesso!");
      }

      handleCancelar();
      carregarServicos();
    } catch (err) {
      alert("Erro ao salvar serviço. Verifique os campos.");
    }
  };

  const prepararEdicao = (s) => {
    setEditandoId(s.id);
    setNovoServico({
      nome: s.nome,
      preco: s.preco,
      descricao: s.descricao || "",
    });
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const handleCancelar = () => {
    setEditandoId(null);
    setNovoServico(estadoInicial);
  };

  const handleDesativar = async (id) => {
    if (window.confirm("Deseja desativar este serviço?")) {
      try {
        await api.delete(`/especialidades/${id}`);
        carregarServicos();
      } catch (err) {
        alert(
          "Erro ao desativar. O serviço pode estar vinculado a agendamentos.",
        );
      }
    }
  };

  const handleReativar = async (id) => {
    try {
      await api.put(`/especialidades/${id}/reativar`);
      alert("Serviço reativado!");
      carregarServicos();
    } catch (err) {
      alert("Erro ao reativar.");
    }
  };

  return (
    <Layout titulo="Catálogo de Especialidades">
      <ServicoStats totalAtivos={totalAtivos} ticketMedio={ticketMedio} />

      <ServicoForm
        novoServico={novoServico}
        setNovoServico={setNovoServico}
        onSubmit={handleSalvar}
        editandoId={editandoId}
        onCancelar={handleCancelar}
      />

      <ServicoTable
        servicos={servicosFiltrados}
        busca={busca}
        setBusca={setBusca}
        verInativos={verInativos}
        setVerInativos={setVerInativos}
        onEdit={prepararEdicao}
        onDesativar={handleDesativar}
        onReativar={handleReativar}
        loading={loading}
      />
    </Layout>
  );
}

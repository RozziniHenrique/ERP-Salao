import React, { useState, useEffect } from "react";
import api from "../../services/api";
import Layout from "../../components/layout";
import FuncionarioForm from "./components/FuncionarioForm";
import FuncionarioTable from "./components/FuncionarioTable";
import FuncionarioModal from "./components/FuncionarioModal";
import { getErrorMessage } from "@/utils/errorHandler";

export default function Funcionarios() {
  // --- ESTADOS ---
  const [funcionarios, setFuncionarios] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [loading, setLoading] = useState(true);

  // Filtros
  const [buscaNome, setBuscaNome] = useState("");
  const [filtroEsp, setFiltroEsp] = useState("");
  const [verInativos, setVerInativos] = useState(false);

  // Modais e Edição
  const [funcSelecionado, setFuncSelecionado] = useState(null);
  const [editandoId, setEditandoId] = useState(null);

  const estadoInicial = {
    nome: "",
    email: "",
    cpf: "",
    telefone: "",
    role: "FUNCIONARIO",
    especialidadesIds: [],
    endereco: {
      cep: "",
      logradouro: "",
      numero: "",
      bairro: "",
      cidade: "",
      uf: "",
    },
  };

  const [novoFunc, setNovoFunc] = useState(estadoInicial);

  // --- CARREGAMENTO DE DADOS ---
  const carregarDados = async () => {
    try {
      setLoading(true);
      const [resFunc, resEsp] = await Promise.all([
        api.get(`/funcionarios?ativos=${!verInativos}`),
        api.get("/especialidades"),
      ]);

      const listaBruta = resFunc.data.content || resFunc.data || [];
      // Remove o admin/id 1 se necessário
      setFuncionarios(listaBruta.filter((f) => f.id !== 1));
      setEspecialidades(resEsp.data.content || resEsp.data || []);
    } catch (err) {
      console.error("Erro ao carregar dados:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarDados();
  }, [verInativos]);

  const funcionariosFiltrados = funcionarios.filter((f) => {
    const matchesNome = (f.nome || "")
      .toLowerCase()
      .includes(buscaNome.toLowerCase());
    const matchesEsp =
      filtroEsp === ""
        ? true
        : f.especialidades?.some((e) => String(e.id) === filtroEsp);
    return matchesNome && matchesEsp;
  });

  // --- AÇÕES DO FORMULÁRIO ---
  const handleSave = async (e) => {
    e.preventDefault();
    try {
      const payloadLimpo = {
        nome: novoFunc.nome,
        email: novoFunc.email,
        role: novoFunc.role || "FUNCIONARIO",
        cpf: (novoFunc.cpf || "").replace(/\D/g, ""),
        telefone: (novoFunc.telefone || "").replace(/\D/g, ""),
        endereco: {
          ...novoFunc.endereco,
          cep: (novoFunc.endereco?.cep || "").replace(/\D/g, ""),
          logradouro: novoFunc.endereco?.logradouro || "",
          numero: novoFunc.endereco?.numero || "",
          bairro: novoFunc.endereco?.bairro || "",
          cidade: novoFunc.endereco?.cidade || "",
          uf: novoFunc.endereco?.uf || "",
        },
        especialidadesIds: (novoFunc.especialidadesIds || []).map(Number),
      };
      if (editandoId) {
        await api.put(`/funcionarios`, { ...payloadLimpo, id: editandoId });
        alert("Profissional atualizado!");
      } else {
        await api.post("/funcionarios", payloadLimpo);
        alert("Cadastrado com sucesso!");
      }
      handleCancelar();
      carregarDados();
    } catch (err) {
      const msg = getErrorMessage(err);
      alert(msg);
      console.error("Erro completo:", err);
    }
  };

  const prepararEdicao = (f) => {
    setEditandoId(f.id);

    setNovoFunc({
      ...f,
      role: f.role || "FUNCIONARIO",
      endereco: f.endereco
        ? { ...f.endereco }
        : {
            cep: "",
            logradouro: "",
            numero: "",
            bairro: "",
            cidade: "",
            uf: "",
          },
      especialidadesIds: f.especialidades
        ? f.especialidades.map((e) => String(e.id))
        : [],
    });
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const handleCancelar = () => {
    setEditandoId(null);
    setNovoFunc(estadoInicial);
  };

  const buscarCep = async (cep) => {
    const cepLimpo = cep.replace(/\D/g, "");
    if (cepLimpo.length !== 8) return;

    try {
      const res = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
      const data = await res.json();

      if (!data.erro) {
        setNovoFunc((prev) => ({
          ...prev,
          endereco: {
            ...prev.endereco,
            logradouro: data.logradouro,
            bairro: data.bairro,
            cidade: data.localidade,
            uf: data.uf,
            cep: cep,
          },
        }));
      }
    } catch (err) {
      console.error("Erro ao buscar CEP", err);
    }
  };

  // --- AÇÕES DA TABELA ---
  const handleDesativar = async (id) => {
    if (window.confirm("Deseja desativar este profissional?")) {
      try {
        await api.delete(`/funcionarios/${id}`);
        carregarDados();
      } catch (err) {
        alert("Erro ao desativar.");
      }
    }
  };

  const handleReativar = async (id) => {
    try {
      await api.put(`/funcionarios/${id}/reativar`);
      alert("Profissional reativado!");
      carregarDados();
    } catch (err) {
      alert("Erro ao reativar.");
    }
  };

  const handleRemoverEspecialidade = async (funcionario, espId) => {
    if (window.confirm(`Deseja remover esta especialidade?`)) {
      try {
        await api.delete(`/funcionarios/${funcionario.id}/${espId}`);
        alert("Especialidade removida com sucesso!");
        carregarDados();
      } catch (err) {
        console.error("Erro ao remover:", err);
        alert("Erro ao remover especialidade.");
      }
    }
  };

  const handleVerDetalhes = async (id) => {
    try {
      const response = await api.get(`/funcionarios/${id}`);
      setFuncSelecionado(response.data);
    } catch (err) {
      alert("Erro ao buscar detalhes.");
    }
  };

  return (
    <Layout titulo="Gestão de Equipe">
      <FuncionarioForm
        novoFunc={novoFunc}
        setNovoFunc={setNovoFunc}
        onSubmit={handleSave}
        editandoId={editandoId}
        onCancelar={handleCancelar}
        especialidades={especialidades}
        buscarCep={buscarCep}
      />

      <FuncionarioTable
        funcionarios={funcionariosFiltrados}
        especialidades={especialidades}
        buscaNome={buscaNome}
        setBuscaNome={setBuscaNome}
        filtroEsp={filtroEsp}
        setFiltroEsp={setFiltroEsp}
        verInativos={verInativos}
        setVerInativos={setVerInativos}
        onEdit={prepararEdicao}
        onDesativar={handleDesativar}
        onReativar={handleReativar}
        onRemoverEsp={handleRemoverEspecialidade}
        onVerDetalhes={handleVerDetalhes}
        loading={loading}
      />

      <FuncionarioModal
        func={funcSelecionado}
        onClose={() => setFuncSelecionado(null)}
      />
    </Layout>
  );
}

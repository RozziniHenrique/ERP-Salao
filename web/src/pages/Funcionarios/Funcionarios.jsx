import React, { useState, useEffect } from "react";
import api from "../../services/api";
import Layout from "../../components/layout";
import FuncionarioForm from "./components/FuncionarioForm";
import FuncionarioTable from "./components/FuncionarioTable";
import FuncionarioModal from "./components/FuncionarioModal";
import { getErrorMessage } from "@/utils/errorHandler";

export default function Funcionarios() {
  const [funcionarios, setFuncionarios] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [loading, setLoading] = useState(true);

  const [buscaNome, setBuscaNome] = useState("");
  const [filtroEsp, setFiltroEsp] = useState("");
  const [verInativos, setVerInativos] = useState(false);

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

  const carregarDados = async () => {
    try {
      setLoading(true);
      const [resFunc, resEsp] = await Promise.all([
        api.get(`/funcionarios?ativos=${!verInativos}`),
        api.get("/especialidades"),
      ]);

      const listaBruta = resFunc.data.content || resFunc.data || [];
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

  useEffect(() => {
    if (!editandoId && novoFunc.nome) {
      const nomeLimpo = novoFunc.nome
        .trim()
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");

      const partes = nomeLimpo.split(" ");

      if (partes.length >= 2) {
        const primeiroNome = partes[0];
        const ultimoNome = partes[partes.length - 1];
        const emailGerado = `${primeiroNome}.${ultimoNome}@stefer.com`;

        setNovoFunc((prev) => ({
          ...prev,
          email: emailGerado,
        }));
      }
    }
  }, [novoFunc.nome, editandoId]);

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

  const handleSave = async (e) => {
    e.preventDefault();
    console.log("IDs que estão sendo enviados:", novoFunc.especialidadesIds);
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

  const handleToggleEspecialidade = async (espId) => {
    const idStr = String(espId);
    const espSelect = novoFunc.especialidadesIds.includes(idStr);

    if (editandoId && espSelect) {
      try {
        const confirma = window.confirm(
          "Deseja remover a especialidade agora?",
        );

        if (confirma) {
          await api.delete(`/funcionarios/${editandoId}/${espId}`);

          setNovoFunc({
            ...novoFunc,
            especialidadesIds: novoFunc.especialidadesIds.filter(
              (id) => id !== idStr,
            ),
          });

          alert("Especialidade removida com sucesso");
        }
      } catch (error) {
        console.error("Erro ao remover:", error);
        alert("Não foi possivel remover a especialidade do especialista");
      }
    } else {
      const novas = espSelect
        ? novoFunc.especialidadesIds.filter((i) => i !== idStr)
        : [...novoFunc.especialidadesIds, idStr];

      setNovoFunc({ ...novoFunc, especialidadesIds: novas });
    }
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
        handleToggleEspecialidade={handleToggleEspecialidade}
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

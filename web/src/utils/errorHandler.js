export const getErrorMessage = (err) => {
  console.log("Estrutura do erro recebida:", err.response?.data);

  const erroData = err.response?.data;

  if (erroData?.mensagem) {
    return erroData.mensagem;
  }

  if (Array.isArray(erroData)) {
    return erroData.map((e) => `${e.campo}: ${e.mensagem}`).join("\n");
  }

  if (erroData?.error || erroData?.message) {
    return erroData.message || erroData.error;
  }

  if (err.response?.status === 403) {
    return "Acesso negado: Você não tem permissão ou sua sessão expirou.";
  }

  return "Erro inesperado ao processar a requisição.";
};

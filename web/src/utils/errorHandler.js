// utils/errorHandler.js
export const getErrorMessage = (err) => {
  // Log para debug no console (ajuda a ver a estrutura real que o Java mandou)
  console.log("Estrutura do erro recebida:", err.response?.data);

  const erroData = err.response?.data;

  // 1. Se o Java mandou o DadosErroMensagem { mensagem: "..." }
  if (erroData?.mensagem) {
    return erroData.mensagem;
  }

  // 2. Se o Java mandou a lista de erros de validação @Valid
  if (Array.isArray(erroData)) {
    return erroData.map((e) => `${e.campo}: ${e.mensagem}`).join("\n");
  }

  // 3. Se for um erro padrão do Spring Security (403)
  if (erroData?.error || erroData?.message) {
    return erroData.message || erroData.error;
  }

  // 4. Se o status for 403 e não veio corpo (Spring Security barrou antes)
  if (err.response?.status === 403) {
    return "Acesso negado: Você não tem permissão ou sua sessão expirou.";
  }

  return "Erro inesperado ao processar a requisição.";
};

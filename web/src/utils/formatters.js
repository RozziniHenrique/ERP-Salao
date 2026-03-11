export const formatarMoeda = (valor) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    valor || 0,
  );

export const formatarHora = (dataString) => {
  if (!dataString) return "--:--";
  const data = new Date(dataString);

  const hora = data.getHours().toString().padStart(2, "0");
  return `${hora}:00`;
};

export const formatarDataISO = (data) =>
  new Date(data).toISOString().split("T")[0];

export const formatarMoeda = (valor) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    valor || 0,
  );

export const formatarHora = (data) =>
  data
    ? new Date(data).toLocaleTimeString("pt-BR", {
        hour: "2-digit",
        minute: "2-digit",
      })
    : "--:--";

export const formatarDataISO = (data) =>
  new Date(data).toISOString().split("T")[0];

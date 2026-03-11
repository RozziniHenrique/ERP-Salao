import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const user = JSON.parse(localStorage.getItem("user"));
  if (user && user.token) {
    config.headers.Authorization = `Bearer ${user.token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (
      error.response &&
      (error.response.status === 403 || error.response.status === 401)
    ) {
      //console.warn("Sessão expirada. Limpando token...");
      //localStorage.removeItem("user");
      //window.location.href = "/login";
      console.error(
        "Erro de permissão ou sessão, mas mantendo usuário logado para debug.",
      );
    }
    return Promise.reject(error);
  },
);
export default api;

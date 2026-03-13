# 💈 Projeto de Estudo - Gestão de Franquias de Beleza

API de alto desempenho para **Gestão de Franquias** e centros de estética, focada em inteligência de negócios, segurança de dados e automação de regras de agendamento.

---

### 🚀 Novidades da Versão 2.0

- **Business Intelligence:** Dashboard integrado com faturamento em tempo real, ranking de performance e alertas de qualidade (notas < 4.0).
- **Arquitetura Clean (DDD):** Refatoração completa para o padrão de domínios, isolando regras de negócio de infraestrutura para maior escalabilidade.
- **Refatoração Frontend:** Modularização de componentes e implementação de _Path Aliases_ (`@/`) para código limpo.

---

### 🛠️ Stack Tecnológica

- **Backend:** Java 21 + Spring Boot 3 (Spring Security + JWT)
- **Frontend Web:** React + Vite + Tailwind CSS (Gestão Administrativa)
- **Mobile:** React Native (App Cliente - _Em espera_)
- **Persistência:** MySQL + Flyway
- **Qualidade:** JUnit 5 + Mockito

---

### 📂 Estrutura do Ecossistema

O projeto está dividido em frentes específicas para atender diferentes perfis de usuário:

- **`/api`**: Core do sistema, regras de negócio e inteligência de dados.
- **`/web`**: Painel administrativo para gestão de franqueados e profissionais (Faturamento, Relatórios).
- **`/mobile`**: Interface do cliente para agendamentos e acompanhamento de serviços. -> _Desenvolvimento em espera_

---

### 📏 Regras de Negócio & Validações

O sistema utiliza o padrão **Strategy** para aplicar regras automáticas:

- **Horário de Funcionamento:** Segunda a Sábado, das 07h às 19h.
- **Antecedência:** Mínimo de 30 min para agendar e 2h para cancelar.
- **Prevenção de Conflitos:** Validação de duplicidade de horário para profissional e cliente.
- **Gestão de Qualidade:** Alertas para colaboradores com média de avaliação inferior a 4.0.

---

### 📊 Principais Endpoints

| Método   | Endpoint                  | Descrição                                                      |
| :------- | :------------------------ | :------------------------------------------------------------- |
| **GET**  | `/dashboard`              | Retorna faturamento do dia, ranking de funcionários e alertas. |
| **POST** | `/agendamentos`           | Cria agendamento validando todas as regras de negócio.         |
| **PUT**  | `/agendamentos/finalizar` | Conclui o serviço e registra a nota (atualiza faturamento).    |
| **GET**  | `/relatorio/estatisticas` | Mostra quais especialidades são as mais rentáveis.             |

---

### ✍️ Como rodar o projeto

**Backend (API):**

1. **Banco de Dados:** Crie um banco MySQL chamado `stefer`.
2. **Configuração:** Ajuste as credenciais em `api/src/main/resources/application.properties`.
3. **Execução:**

```bash
cd api
./mvnw spring-boot:run
```

**Frontend (Web):**

1. **Instalação:** ```bash
2. **cd web**
3. **npm install**
4. **Execução:**

```bash
npm run dev
```

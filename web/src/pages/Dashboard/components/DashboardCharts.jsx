import React from "react";
import {
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
  Legend,
} from "recharts";

const COLORS = ["#4318FF", "#6AD2FF", "#EFF4FB", "#82ca9d", "#FFBB28"];

export default function DashboardChart({ agendamentos }) {
  const dataMap = agendamentos.reduce((acc, curr) => {
    const nome =
      curr.especialidade?.nome ||
      (typeof curr.especialidade === "string" ? curr.especialidade : null) ||
      curr.servico ||
      "Outros";
    acc[nome] = (acc[nome] || 0) + 1;
    return acc;
  }, {});

  const data = Object.keys(dataMap).map((name) => ({
    name,
    value: dataMap[name],
  }));

  return (
    <div
      style={{
        background: "#fff",
        padding: "20px",
        borderRadius: "20px",
        height: "350px",
        display: "flex",
        flexDirection: "column",
        minWidth: "0",
        boxShadow: "0px 10px 30px rgba(0, 0, 0, 0.05)",
      }}
    >
      <h4 style={{ color: "#2B3674", marginBottom: "20px" }}>
        Demanda por Serviço
      </h4>
      <ResponsiveContainer width="100%" height="100%">
        <PieChart>
          <Pie
            data={data}
            innerRadius={60}
            outerRadius={80}
            paddingAngle={5}
            dataKey="value"
            cx="50%"
            cy="50%"
          >
            {data.map((_, index) => (
              <Cell
                key={`cell-${index}`}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
          <Legend
            verticalAlign="bottom"
            align="center"
            layout="horizontal"
            iconType="circle"
            wrapperStyle={{ paddingTop: "20px" }}
          />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}

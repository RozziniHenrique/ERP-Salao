import React from "react";
import * as S from "../Funcionarios.styles";
import { maskCPF, maskPhone, maskCEP } from "@/utils/masks";

export default function FuncionarioModal({ func, onClose }) {
  if (!func) return null;

  return (
    <div style={S.styles.modalOverlay} onClick={onClose}>
      <div style={S.styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <h2 style={S.styles.cardTitle}>Dados do Profissional</h2>
        <div style={{ color: "#2B3674", lineHeight: "1.6" }}>
          <p>
            <strong>Nome:</strong> {func.nome}
          </p>
          <p>
            <strong>CPF:</strong> {maskCPF(func.cpf || "")}
          </p>
          <p>
            <strong>E-mail:</strong> {func.email}
          </p>
          <p>
            <strong>Telefone:</strong> {maskPhone(func.telefone || "")}
          </p>
          <hr style={{ border: "0.5px solid #F4F7FE", margin: "15px 0" }} />
          <p
            style={{
              fontSize: "0.8rem",
              color: "#A3AED0",
              textTransform: "uppercase",
              fontWeight: "bold",
            }}
          >
            Endereço
          </p>
          {func.endereco ? (
            <>
              <p>
                {func.endereco.logradouro}, {func.endereco.numero}
              </p>
              <p>
                {func.endereco.bairro} - {maskCEP(func.endereco.cep || "")}
              </p>
              <p>
                {func.endereco.cidade} / {func.endereco.uf}
              </p>
            </>
          ) : (
            <p>Endereço não cadastrado.</p>
          )}
        </div>
        <button
          onClick={onClose}
          style={{ ...S.styles.btnPrimary, marginTop: "20px", width: "100%" }}
        >
          FECHAR
        </button>
      </div>
    </div>
  );
}

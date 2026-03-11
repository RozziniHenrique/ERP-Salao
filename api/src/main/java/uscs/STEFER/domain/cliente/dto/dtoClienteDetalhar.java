package uscs.STEFER.domain.cliente.dto;

import uscs.STEFER.domain.cliente.Cliente;
import uscs.STEFER.domain.endereco.Endereco;

public record dtoClienteDetalhar(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public dtoClienteDetalhar(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEndereco());
    }
}

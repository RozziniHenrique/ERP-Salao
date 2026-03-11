package uscs.STEFER.domain.cliente.dto;


import uscs.STEFER.domain.cliente.Cliente;

public record dtoClienteListar(Long id, String nome, String email, String cpf) {

    public dtoClienteListar(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf());
    }
}

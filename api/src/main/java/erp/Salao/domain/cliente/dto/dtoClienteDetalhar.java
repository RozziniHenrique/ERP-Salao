package erp.Salao.domain.cliente.dto;

import erp.Salao.domain.cliente.Cliente;
import erp.Salao.domain.endereco.Endereco;

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

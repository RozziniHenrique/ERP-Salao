package erp.Salao.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record dtoEndereco(
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,
        
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,
        
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String cep,
        
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,
        
        @NotBlank(message = "UF é obrigatória")
        String uf,
        
        String complemento,
        
        @NotBlank(message = "Número é obrigatório")
        String numero 
) {}

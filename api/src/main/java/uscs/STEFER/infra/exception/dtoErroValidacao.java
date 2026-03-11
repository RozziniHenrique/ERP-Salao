package uscs.STEFER.infra.exception;


import org.springframework.validation.FieldError;

public record dtoErroValidacao(String campo, String mensagem) {
    public dtoErroValidacao(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}

package uscs.STEFER.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {
    

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(new DadosErroMensagem(ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity tratarErroIntegridade(org.springframework.dao.DataIntegrityViolationException ex) {
        String mensagem = "Erro de integridade: CPF ou E-mail já cadastrado.";

        String causa = ex.getMostSpecificCause().getMessage().toLowerCase();
        if (causa.contains("cpf")) {
            mensagem = "Este CPF já está cadastrado no sistema.";
        } else if (causa.contains("email")) {
            mensagem = "Este E-mail já está cadastrado no sistema.";
        }

        return ResponseEntity.badRequest().body(new DadosErroMensagem(mensagem));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400Leitura(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new DadosErroMensagem("Erro na leitura do JSON: " + ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400Validacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErroMensagem(ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado"));
    }

    private record DadosErroMensagem(String mensagem) {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DadosErroMensagem("Erro interno: " + ex.getLocalizedMessage()));
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}

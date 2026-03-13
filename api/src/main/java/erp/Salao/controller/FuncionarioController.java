package erp.Salao.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import erp.Salao.domain.funcionario.dto.*;
import erp.Salao.service.FuncionarioService;

@RestController
@RequestMapping("funcionarios")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid dtoFuncionarioCadastrar dados) {
        var funcionario = service.cadastrar(dados);
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'FUNCIONARIO')")
    public ResponseEntity atualizar(@RequestBody @Valid dtoFuncionarioAtualizar dados) {
        var funcionario = service.atualizar(dados);
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<dtoFuncionarioListar>> listar(
            @RequestParam(required = false) Long idEspecialidade,
            @RequestParam(required = false, defaultValue = "true") Boolean ativos,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var pagina = service.listar(idEspecialidade, ativos, paginacao);
    return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var funcionario = service.detalhar(id);
        return ResponseEntity.ok(new dtoFuncionarioDetalhar(funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    public ResponseEntity reativar(@PathVariable Long id) {
        service.reativar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idFuncionario}/{idEspecialidade}")
    public ResponseEntity removerEspecialidade(@PathVariable Long idFuncionario, @PathVariable Long idEspecialidade) {
        service.removerEspecialidade(idFuncionario, idEspecialidade);
        return ResponseEntity.noContent().build();
    }
}
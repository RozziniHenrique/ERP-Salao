package erp.Salao.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import erp.Salao.domain.cliente.dto.*;
import erp.Salao.service.ClienteService;

@RestController
@RequestMapping("clientes")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity cadastrar(@RequestBody @Valid dtoClienteCadastrar dados) {
        var cliente = service.cadastrar(dados);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'CLIENTE')")
    public ResponseEntity atualizar(@RequestBody @Valid dtoClienteAtualizar dados) {
        var cliente = service.atualizar(dados);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<dtoClienteListar>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'CLIENTE')")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var cliente = service.detalhar(id);
        return ResponseEntity.ok(new dtoClienteDetalhar(cliente));
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
}
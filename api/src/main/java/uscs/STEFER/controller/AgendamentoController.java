package uscs.STEFER.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uscs.STEFER.domain.agendamento.AgendamentoRepository;
import uscs.STEFER.domain.agendamento.dto.*;
import uscs.STEFER.domain.avaliacao.dto.dtoAvaliacaoCadastrar;
import uscs.STEFER.domain.usuario.Usuario;
import uscs.STEFER.service.AgendamentoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'FUNCIONARIO', 'CLIENTE')")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid dtoAgendamentoCadastrar dados, UriComponentsBuilder uriBuilder) {
        var dto = service.agendar(dados);
        var uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<dtoAgendamentoListar>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Long idFuncionario,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) Long idEspecialidade,
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao,
            @AuthenticationPrincipal Usuario logado) {
        
        var page = service.listar(data, idFuncionario, idCliente, idEspecialidade, paginacao, logado);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var dto = service.detalhar(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/finalizar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR', 'FUNCIONARIO')")
    public ResponseEntity finalizar(@RequestBody @Valid dtoAgendamentoFinalizar dados) {
        service.finalizar(dados);
        return ResponseEntity.ok("Atendimento finalizado com sucesso!");
    }

    @PostMapping("/{id}/avaliar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity avaliar(@PathVariable Long id, @RequestBody @Valid dtoAvaliacaoCadastrar dados) {
        var dto = service.avaliar(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid dtoAgendamentoCancelar dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/estatisticas")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")
    public ResponseEntity<List<dtoAgendamentoRelatorioEspecialidade>> relatorioEspecialidades() {
        return ResponseEntity.ok(repository.contagemPorEspecialidade());
    }
}
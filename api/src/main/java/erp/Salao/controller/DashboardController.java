package erp.Salao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.dashboard.dto.dtoDashboard;
import erp.Salao.service.DashboardService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasAnyAuthority('ADMIN', 'GESTOR')")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping
    public ResponseEntity obterDadosDashboard(
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fim) {
        
        var dados = service.obterDadosDashboard(inicio, fim);
        return ResponseEntity.ok(dados);
    }
}

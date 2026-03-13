package erp.Salao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.Salao.domain.agendamento.AgendamentoRepository;
import erp.Salao.domain.dashboard.dto.dtoDashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public dtoDashboard obterDadosDashboard(LocalDate inicio, LocalDate fim) {
        var dataInicio = (inicio != null) ? inicio.atStartOfDay() : LocalDate.now().atStartOfDay();
        var dataFim = (fim != null) ? fim.atTime(23, 59, 59) : LocalDate.now().atTime(23, 59, 59);

        var faturamentoBruto = agendamentoRepository.calcularFaturamentoTotal(dataInicio, dataFim);
        var faturamentoFinal = (faturamentoBruto != null) ? faturamentoBruto : BigDecimal.ZERO;

        return new dtoDashboard(
                faturamentoFinal,
                agendamentoRepository.findRankingFuncionarios(),
                agendamentoRepository.findPioresAvaliados(),
                agendamentoRepository.count()
        );
    }
}
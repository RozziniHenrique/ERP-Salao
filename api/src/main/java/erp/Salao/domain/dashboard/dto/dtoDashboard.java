package erp.Salao.domain.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

public record dtoDashboard(
        BigDecimal faturamentoHoje,
        List<RankingFuncionario> ranking,
        List<AlertaQualidade> alertas,
        Long totalGeral
) {
}

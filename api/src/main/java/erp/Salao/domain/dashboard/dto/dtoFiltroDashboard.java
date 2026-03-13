package erp.Salao.domain.dashboard.dto;

import java.time.LocalDate;

public record dtoFiltroDashboard(
    LocalDate dataInicio,
    LocalDate dataFim
) {}

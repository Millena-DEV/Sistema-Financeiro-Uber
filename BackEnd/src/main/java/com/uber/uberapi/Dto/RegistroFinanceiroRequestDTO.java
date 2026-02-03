package com.uber.uberapi.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegistroFinanceiroRequestDTO {

    @NotNull
    private LocalDate data;

    @NotNull
    @PositiveOrZero
    private BigDecimal kmRodado;

    @NotNull
    @PositiveOrZero
    private BigDecimal ganhosBrutos;

    @NotNull
    @Positive
    private BigDecimal precoGasolina;

    @NotNull
    @Positive
    private BigDecimal litrosAbastecidos;

    @PositiveOrZero
    private BigDecimal horasTrabalhadas;
}

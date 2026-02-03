package com.uber.uberapi.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegistroFinanceiroResponseDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal kmRodado;
    private BigDecimal ganhosBrutos;
    private BigDecimal precoGasolina;
    private BigDecimal litrosAbastecidos;
    private BigDecimal horasTrabalhadas;

    private BigDecimal gastoGasolina;
    private BigDecimal lucroLiquido;
    private BigDecimal custoPorKm;
    private BigDecimal lucroPorKm;
    private BigDecimal ganhoPorHora;
}

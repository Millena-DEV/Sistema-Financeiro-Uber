package com.uber.uberapi.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ResumoFinanceiroDTO {

    private LocalDate inicio;
    private LocalDate fim;
    private BigDecimal kmTotal;
    private BigDecimal ganhosBrutosTotal;
    private BigDecimal gastoGasolinaTotal;
    private BigDecimal lucroLiquidoTotal;
    private BigDecimal custoPorKm;
    private BigDecimal lucroPorKm;
    private BigDecimal ganhoPorHora;
    private BigDecimal horasTrabalhadasTotal;
}

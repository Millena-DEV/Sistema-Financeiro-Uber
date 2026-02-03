package com.uber.uberapi.Service;

import com.uber.uberapi.Dto.RegistroFinanceiroRequestDTO;
import com.uber.uberapi.Dto.RegistroFinanceiroResponseDTO;
import com.uber.uberapi.Dto.ResumoFinanceiroDTO;
import com.uber.uberapi.Entity.RegistroFinanceiro;
import com.uber.uberapi.Repository.RegistroFinanceiroRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class RegistroFinanceiroService {

    private static final BigDecimal ZERO = new BigDecimal("0");
    private static final int SCALE_MONEY = 2;
    private static final int SCALE_RATE = 4;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private final RegistroFinanceiroRepository registroFinanceiroRepository;

    public RegistroFinanceiroService(RegistroFinanceiroRepository registroFinanceiroRepository) {
        this.registroFinanceiroRepository = registroFinanceiroRepository;
    }

    public RegistroFinanceiroResponseDTO cadastrar(RegistroFinanceiroRequestDTO dto) {
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setData(dto.getData());
        registro.setKmRodado(dto.getKmRodado());
        registro.setGanhosBrutos(dto.getGanhosBrutos());
        registro.setPrecoGasolina(dto.getPrecoGasolina());
        registro.setLitrosAbastecidos(dto.getLitrosAbastecidos());
        registro.setHorasTrabalhadas(dto.getHorasTrabalhadas());

        RegistroFinanceiro salvo = registroFinanceiroRepository.save(registro);
        return mapResponse(salvo);
    }

    public List<RegistroFinanceiroResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return registroFinanceiroRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(this::mapResponse)
                .toList();
    }

    public ResumoFinanceiroDTO resumo(LocalDate inicio, LocalDate fim) {
        List<RegistroFinanceiro> registros = registroFinanceiroRepository.findByDataBetween(inicio, fim);

        BigDecimal kmTotal = ZERO;
        BigDecimal ganhosBrutosTotal = ZERO;
        BigDecimal gastoGasolinaTotal = ZERO;
        BigDecimal horasTotal = ZERO;

        for (RegistroFinanceiro registro : registros) {
            kmTotal = kmTotal.add(nullSafe(registro.getKmRodado()));
            ganhosBrutosTotal = ganhosBrutosTotal.add(nullSafe(registro.getGanhosBrutos()));
            gastoGasolinaTotal = gastoGasolinaTotal.add(calcularGastoGasolina(registro));
            horasTotal = horasTotal.add(nullSafe(registro.getHorasTrabalhadas()));
        }

        BigDecimal lucroLiquidoTotal = ganhosBrutosTotal.subtract(gastoGasolinaTotal);

        ResumoFinanceiroDTO resumo = new ResumoFinanceiroDTO();
        resumo.setInicio(inicio);
        resumo.setFim(fim);
        resumo.setKmTotal(kmTotal.setScale(SCALE_MONEY, ROUNDING));
        resumo.setGanhosBrutosTotal(ganhosBrutosTotal.setScale(SCALE_MONEY, ROUNDING));
        resumo.setGastoGasolinaTotal(gastoGasolinaTotal.setScale(SCALE_MONEY, ROUNDING));
        resumo.setLucroLiquidoTotal(lucroLiquidoTotal.setScale(SCALE_MONEY, ROUNDING));
        resumo.setHorasTrabalhadasTotal(horasTotal.setScale(SCALE_MONEY, ROUNDING));

        if (kmTotal.compareTo(ZERO) > 0) {
            resumo.setCustoPorKm(gastoGasolinaTotal.divide(kmTotal, SCALE_RATE, ROUNDING));
            resumo.setLucroPorKm(lucroLiquidoTotal.divide(kmTotal, SCALE_RATE, ROUNDING));
        }

        if (horasTotal.compareTo(ZERO) > 0) {
            resumo.setGanhoPorHora(lucroLiquidoTotal.divide(horasTotal, SCALE_RATE, ROUNDING));
        }

        return resumo;
    }

    private RegistroFinanceiroResponseDTO mapResponse(RegistroFinanceiro registro) {
        BigDecimal gastoGasolina = calcularGastoGasolina(registro);
        BigDecimal lucroLiquido = nullSafe(registro.getGanhosBrutos()).subtract(gastoGasolina);

        RegistroFinanceiroResponseDTO dto = new RegistroFinanceiroResponseDTO();
        dto.setId(registro.getId());
        dto.setData(registro.getData());
        dto.setKmRodado(registro.getKmRodado());
        dto.setGanhosBrutos(registro.getGanhosBrutos());
        dto.setPrecoGasolina(registro.getPrecoGasolina());
        dto.setLitrosAbastecidos(registro.getLitrosAbastecidos());
        dto.setHorasTrabalhadas(registro.getHorasTrabalhadas());
        dto.setGastoGasolina(gastoGasolina.setScale(SCALE_MONEY, ROUNDING));
        dto.setLucroLiquido(lucroLiquido.setScale(SCALE_MONEY, ROUNDING));

        if (registro.getKmRodado() != null && registro.getKmRodado().compareTo(ZERO) > 0) {
            dto.setCustoPorKm(gastoGasolina.divide(registro.getKmRodado(), SCALE_RATE, ROUNDING));
            dto.setLucroPorKm(lucroLiquido.divide(registro.getKmRodado(), SCALE_RATE, ROUNDING));
        }

        if (registro.getHorasTrabalhadas() != null && registro.getHorasTrabalhadas().compareTo(ZERO) > 0) {
            dto.setGanhoPorHora(lucroLiquido.divide(registro.getHorasTrabalhadas(), SCALE_RATE, ROUNDING));
        }

        return dto;
    }

    private BigDecimal calcularGastoGasolina(RegistroFinanceiro registro) {
        if (registro.getPrecoGasolina() == null || registro.getLitrosAbastecidos() == null) {
            return ZERO;
        }
        return registro.getPrecoGasolina().multiply(registro.getLitrosAbastecidos());
    }

    private BigDecimal nullSafe(BigDecimal value) {
        return value == null ? ZERO : value;
    }
}

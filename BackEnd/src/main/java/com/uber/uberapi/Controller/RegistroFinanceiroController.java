package com.uber.uberapi.Controller;

import com.uber.uberapi.Dto.RegistroFinanceiroRequestDTO;
import com.uber.uberapi.Dto.RegistroFinanceiroResponseDTO;
import com.uber.uberapi.Dto.ResumoFinanceiroDTO;
import com.uber.uberapi.Service.RegistroFinanceiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/financeiro")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistroFinanceiroController {

    private final RegistroFinanceiroService registroFinanceiroService;

    public RegistroFinanceiroController(RegistroFinanceiroService registroFinanceiroService) {
        this.registroFinanceiroService = registroFinanceiroService;
    }

    @PostMapping("/registros")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroFinanceiroResponseDTO cadastrar(@RequestBody @Valid RegistroFinanceiroRequestDTO dto) {
        return registroFinanceiroService.cadastrar(dto);
    }

    @GetMapping("/registros")
    public List<RegistroFinanceiroResponseDTO> listarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return registroFinanceiroService.listarPorPeriodo(inicio, fim);
    }

    @GetMapping("/resumo")
    public ResumoFinanceiroDTO resumo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return registroFinanceiroService.resumo(inicio, fim);
    }
}

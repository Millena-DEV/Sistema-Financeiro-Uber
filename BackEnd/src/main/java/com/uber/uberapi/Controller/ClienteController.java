package com.uber.uberapi.Controller;


import com.uber.uberapi.Dto.ClienteRequestDTO;
import com.uber.uberapi.Dto.ClienteResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.uber.uberapi.Entity.Clientes;
import com.uber.uberapi.Service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

private final ClienteService  clienteService;

public ClienteController(ClienteService clienteService) {

    this.clienteService = clienteService;
    }

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Clientes cadastrar(
        @RequestBody@Valid ClienteRequestDTO dto) {
    return clienteService.cadastrar(dto);
}

@GetMapping
public List<ClienteResponseDTO>listar(){

    return clienteService.listar();
    }
}


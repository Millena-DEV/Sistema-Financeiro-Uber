package com.uber.uberapi.Service;

import java.util.List;

import com.uber.uberapi.Dto.ClienteRequestDTO;
import com.uber.uberapi.Dto.ClienteResponseDTO;
import com.uber.uberapi.Exception.RegraNegocioException;
import com.uber.uberapi.Usuario.RoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uber.uberapi.Entity.Clientes;
import com.uber.uberapi.Repository.ClienteRepository;

@Service
public class ClienteService {

        private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {

            this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Clientes cadastrar(ClienteRequestDTO dto) {

        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RegraNegocioException("Email já cadastrado");
        }

        Clientes cliente=new Clientes();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setRole(RoleEnum.CLIENTE);
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));

        return clienteRepository.save(cliente);
    }

    public List<ClienteResponseDTO>listar() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> {
                    ClienteResponseDTO dto=new ClienteResponseDTO();
                    dto.setId(cliente.getId());
                    dto.setNome(cliente.getNome());
                    dto.setEmail(cliente.getEmail());
                    return dto;
                })
                .toList();
    }





}

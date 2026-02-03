package com.uber.uberapi.Security.Jwt;

import com.uber.uberapi.Auth.Dto.LoginRequestDTO;
import com.uber.uberapi.Entity.Clientes;
import com.uber.uberapi.Exception.RegraNegocioException;
import com.uber.uberapi.Repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(ClienteRepository clienteRepository,
                      PasswordEncoder passwordEncoder,
                      JwtService jwtService) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO dto) {

        Clientes cliente= clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->new RegraNegocioException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(dto.getSenha(), cliente.getSenha())) {
            throw new RegraNegocioException("Email ou senha inválidos");
        }

        return jwtService.gerarToken(
                cliente.getEmail(),
                cliente.getRole()
        );
    }
}

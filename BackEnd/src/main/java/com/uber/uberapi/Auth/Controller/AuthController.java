package com.uber.uberapi.Auth.Controller;

import com.uber.uberapi.Auth.Dto.LoginRequestDTO;
import com.uber.uberapi.Auth.Dto.LoginResponseDTO;
import com.uber.uberapi.Security.Jwt.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // permite Angular
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {

        String token= authService.login(dto);
        return new LoginResponseDTO(token);
    }
}

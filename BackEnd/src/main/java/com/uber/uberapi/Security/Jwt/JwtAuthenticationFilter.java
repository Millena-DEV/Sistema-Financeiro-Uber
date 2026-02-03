package com.uber.uberapi.Security.Jwt;

import com.uber.uberapi.Usuario.RoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtService jwtService;
    private static final Set<String> ROTAS_PUBLICAS = Set.of(
            "/auth/login",
            "/clientes",
            "/financeiro"
    );

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);// Obtém o cabeçalho Authorization

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Remove o prefixo "Bearer "

            if (jwtService.tokenValido(token)) {  // Verifica se o token é válido
                String email = jwtService.getEmailDoToken(token);  // Obtém o e-mail do token
                String role = jwtService.getRoleDoToken(token);  // Obtém o role do token

                // Converte o role para RoleEnum, garantindo que o valor seja válido
                RoleEnum roleEnum = RoleEnum.valueOf(role);

                // Cria a autoridade a partir do RoleEnum
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleEnum.name());

                // Cria o objeto de autenticação com o email e a autoridade
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Adiciona os detalhes da autenticação

                SecurityContextHolder.getContext().setAuthentication(authentication);  // Define a autenticação no contexto
            }
        }

        filterChain.doFilter(request, response);  // Continua com a cadeia de filtros
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) {
            return false;
        }
        if (path.startsWith("/financeiro")) {
            return true;
        }
        return ROTAS_PUBLICAS.contains(path);
    }

}

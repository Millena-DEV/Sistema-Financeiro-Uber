package com.uber.uberapi.Security.Jwt;

import com.uber.uberapi.Usuario.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(String email, RoleEnum role) {
        Date agora = new Date();
        Date expira = new Date(agora.getTime() + expiration);
        // Usando a chave secreta do arquivo de configuração
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(agora)
                .setExpiration(expira)
                .signWith(secretKey)  // Usa a chave secreta configurada
                .compact();
    }


    public boolean tokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);  // Tenta decodificar o token
            return true;
        } catch (Exception e) {
            return false;  // Token inválido ou expirado
        }
    }

    public String getEmailDoToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // Retorna o e-mail do token
    }
    public String getRoleDoToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);  // Retorna o papel (role) do token
    }

}

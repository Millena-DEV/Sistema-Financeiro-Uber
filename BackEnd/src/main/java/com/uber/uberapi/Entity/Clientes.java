package com.uber.uberapi.Entity;

import com.uber.uberapi.Usuario.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Clientes {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotBlank
private String nome;

@Email
@NotBlank
@Column(unique = true)
private String email;

@NotBlank
private String senha;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private RoleEnum role;


}

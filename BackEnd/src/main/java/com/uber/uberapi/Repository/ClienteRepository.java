package com.uber.uberapi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uber.uberapi.Entity.Clientes;

public interface ClienteRepository extends JpaRepository <Clientes,Long> {
    Optional<Clientes>findByEmail(String email);

}

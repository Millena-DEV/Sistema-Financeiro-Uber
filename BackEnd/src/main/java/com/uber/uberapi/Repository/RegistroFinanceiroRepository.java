package com.uber.uberapi.Repository;

import com.uber.uberapi.Entity.RegistroFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroFinanceiroRepository extends JpaRepository<RegistroFinanceiro, Long> {

    List<RegistroFinanceiro> findByDataBetween(LocalDate inicio, LocalDate fim);
}

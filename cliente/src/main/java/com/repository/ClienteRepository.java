package com.repository;

import com.cliente.model.Cliente;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@EnableJpaRepositories
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @NotNull Optional<Cliente> findById(@NotNull String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

}

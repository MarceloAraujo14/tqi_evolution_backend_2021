package com.cadastro.repository;

import com.cadastro.model.Cliente;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@EnableJpaRepositories
@Repository
public interface CadastroRepository extends JpaRepository<Cliente, String> {

    @NotNull Optional<Cliente> findById(@NotNull String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);



}

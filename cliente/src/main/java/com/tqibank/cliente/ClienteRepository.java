package com.tqibank.cliente;

import com.tqibank.cliente.request.RetornoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);
}

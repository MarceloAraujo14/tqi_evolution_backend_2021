package com.cadastro.repository;

import com.cadastro.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Cliente findByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

}

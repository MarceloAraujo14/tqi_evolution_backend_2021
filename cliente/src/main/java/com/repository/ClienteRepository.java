package com.repository;

import com.cliente.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Cliente findByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

}

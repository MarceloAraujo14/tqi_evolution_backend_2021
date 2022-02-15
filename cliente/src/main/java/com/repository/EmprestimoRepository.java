package com.repository;

import com.emprestimo.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EmprestimoRepository extends JpaRepository<Emprestimo, String> {
    List<Emprestimo> findByEmailCliente(String email);

    Emprestimo findByCodigo(String codigo);
}



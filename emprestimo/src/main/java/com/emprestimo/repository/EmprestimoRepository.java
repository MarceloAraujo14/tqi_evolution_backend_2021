package com.emprestimo.repository;

import com.emprestimo.model.Emprestimo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableElasticsearchRepositories
@Repository
public interface EmprestimoRepository extends ElasticsearchRepository<Emprestimo, String> {
    List<Emprestimo> findByEmailCliente(String email);

    void deleteByCodigo(Integer codigo);
}



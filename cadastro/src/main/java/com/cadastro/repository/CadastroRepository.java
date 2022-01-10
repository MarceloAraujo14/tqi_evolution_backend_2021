package com.cadastro.repository;

import com.cadastro.model.Cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@EnableElasticsearchRepositories
@Transactional(readOnly = true)
public interface CadastroRepository extends ElasticsearchRepository<Cliente, String> {
    Cliente findByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

}

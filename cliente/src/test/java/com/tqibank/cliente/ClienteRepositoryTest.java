package com.tqibank.cliente;

/*
-Testes unitários
    O que deve ser testado:
    - Salvar um cliente de forma correta;
    - Retornar um cliente pelo cpf;
    - Retornar um cliente pelo rg;
    - Não salvar um cliente com nome nulo;
    - Não salvar um cliente com algum dado nulo;
    - Não salvar um cliente com dados obrigatórios do endereço nulos;

 */

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.cliente.endereco.tipoEndereco;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    @Name("Deveria salvar um cliente")
    void save(){
        //given
        Cliente cliente = getCliente();
        String email = "jhon.doe@gmail.com";
        cliente.setEmail(email);

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findById(email).isPresent());
    }

    @Test
    @Name("Deveria retornar um cliente por cpf")
    void findByCpf(){
        //given
        Cliente cliente = getCliente();
        String cpf = "35096343065";

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findByCpf(cpf).isPresent());
    }

    @Test
    @Name("Deveria retornar um cliente por rg")
    void findByRg(){
        //given
        Cliente cliente = getCliente();
        String rg = "403829264";

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findByRg(rg).isPresent());
    }

    @Test
    @Name("Não deve salvar um cliente com nome nulo")
    void save2(){
        //given
        Cliente cliente = getCliente();
        cliente.setNome(null);

        //when
        //then
        assertThatThrownBy(() -> repository.save(cliente))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.tqibank.cliente.Cliente.nome")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Name("Não deve salvar um cliente com cpf nulo")
    void save3(){
        //given
        Cliente cliente = getCliente();
        cliente.setCpf(null);

        //when
        //then
        assertThatThrownBy(() -> repository.save(cliente))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.tqibank.cliente.Cliente.cpf")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void DeveNaoSalvarClienteComAlgumDadoObrigatorioDoEnderecoNulo(){
        //given
        Cliente cliente = getCliente();
        cliente.setEnderecos(
                List.of( new Endereco(null, "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));
        //when
        //then
        assertThatThrownBy(() -> repository.save(cliente))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.tqibank.cliente.endereco.Endereco.logradouro")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    public Cliente getCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome("Jhon Doe");
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setRenda(new BigDecimal("5.000"));
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "23520-520", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL)));

        cliente.setEnderecos(List.of(new Endereco("Rua A", "52A", "", "57237970", "Teste",
                "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

        return cliente;
    }

}
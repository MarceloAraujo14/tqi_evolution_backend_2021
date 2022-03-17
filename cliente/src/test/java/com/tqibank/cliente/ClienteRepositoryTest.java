package com.tqibank.cliente;

import com.tqibank.cliente.endereco.Endereco;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    /*@Test
    void DeveriaSalvarUmCliente(){
        //given
        Cliente cliente = new Cliente();
        String email = "jhon.doe@gmail.com";

        cliente.setNome("Jhon Doe");
        cliente.setEmail(email);
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste", "Teste", "Teste")));
        cliente.setRenda(5.000);

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findById(email).isPresent());
    }

    @Test
    void DeveriaRetornarUmClientePorCpf(){
        //given
        Cliente cliente = new Cliente();
        String cpf = "35096343065";

        cliente.setNome("Jhon Doe");
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf(cpf);
        cliente.setRg("403829264");
        cliente.setEnderecos(
               List.of(new Endereco("Rua A", "52A", "", "57237970", "Teste", "Teste", "Teste")));
        cliente.setRenda(5.000);

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findByCpf(cpf).isPresent());
    }

    @Test
    void DeveRetornarErroAoSalvarSemNome(){
        //given
        Cliente cliente = new Cliente();
        cliente.setNome(null);
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setEnderecos(
               List.of(new Endereco("Rua A", "52A", "", "57237970", "Teste", "Teste", "Teste")));
        cliente.setRenda(5.000);
        //when
        //then
        assertThatThrownBy(() ->repository.save(cliente))
                .isInstanceOf(DataIntegrityViolationException.class);
    }*/

}
package com.tqibank.cliente;

/*
-Testes unitários
    O que deve ser testado:
    - Salvar um cliente de forma correta;
    - Retornar um cliente pelo cpf;
    - Não salvar um cliente com nome nulo;
    - Não salvar um cliente com algum dado nulo;
    - Não salvar um cliente com dados obrigatórios do endereço nulos;


 */

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.cliente.endereco.tipoEndereco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

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
    void DeveriaSalvarUmCliente(){
        //given
        Cliente cliente = new Cliente();
        String email = "jhon.doe@gmail.com";

        cliente.setNome("Jhon Doe");
        cliente.setEmail(email);
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setRenda(5.000);
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

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
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

        cliente.setRenda(5.000);

        //when
        this.repository.save(cliente);
        //then
        assertTrue(repository.findByCpf(cpf).isPresent());
    }

    @Test
    void DeveNaoSalvarClienteComNomeNulo(){
        //given
        Cliente cliente = new Cliente();
        cliente.setNome(null);
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

        cliente.setRenda(5.000);
        //when
        //then
        assertThatThrownBy(() -> repository.save(cliente))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.tqibank.cliente.Cliente.nome")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void DeveNaoSalvarClienteComCpfNulo(){
        //given
        Cliente cliente = new Cliente();
        cliente.setNome("Jhon");
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf(null);
        cliente.setRg("403829264");
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

        cliente.setRenda(5.000);
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
        Cliente cliente = new Cliente();
        cliente.setNome("Jhon");
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setEnderecos(
                List.of( new Endereco(null, "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL, cliente)));

        cliente.setRenda(5.000);
        //when
        //then
        assertThatThrownBy(() -> repository.save(cliente))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.tqibank.cliente.endereco.Endereco.logradouro")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
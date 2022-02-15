package com.cadastro.repository;

import com.cadastro.model.Cliente;
import com.cadastro.model.Endereco;
import com.cadastro.model.TipoEndereco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class CadastroRepositoryTest {

    private CadastroRepository cadastroRepository;

    @Autowired
    private CadastroRepositoryTest(CadastroRepository cadastroRepository){
        this.cadastroRepository = cadastroRepository;
    }

    @Test
    void findById() {
        //given

        String id = "jhon.doe@gmail.com";

        Cliente cliente = new Cliente();
        cliente.setNome("Jhon Doe");
        cliente.setEmail(id);
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setEnderecos(List.of(new Endereco(
                "Rua A", "52A", "", "57237970", "Teste", "Teste", "Teste", TipoEndereco.TRABALHO)
        ));
        cliente.setRenda(5.000);

        //when
        this.cadastroRepository.save(cliente);
        //then
        assertTrue(cadastroRepository.findById(id).isPresent());

    }

    @Test
    void testFindByCpf() {
    }

    @Test
    void testFindByRg() {
    }
}
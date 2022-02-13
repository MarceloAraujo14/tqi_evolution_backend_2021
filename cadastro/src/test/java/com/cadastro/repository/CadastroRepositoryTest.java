package com.cadastro.repository;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Endereco;
import com.cadastro.model.TipoEndereco;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CadastroRepositoryTest {

    @Test
    void findByEmail() {
        //given
        ClienteCadastroDTO clienteCadastroDTO = new ClienteCadastroDTO(
                "jhon.doe@gmail.com", "Abc/12345678@", "Jhon Doe", "35096343065", "403829264", 5.000,
                List.of(new Endereco(
                        "Rua A", "52A", "", "57237970", "Teste", "Teste", "Teste", TipoEndereco.TRABALHO)
                )
        );



    }

    @Test
    void findByCpf() {
    }

    @Test
    void findByRg() {
    }
}
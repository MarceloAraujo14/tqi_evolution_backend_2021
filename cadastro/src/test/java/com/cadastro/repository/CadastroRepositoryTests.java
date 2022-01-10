package com.cadastro.repository;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Endereco;
import com.cadastro.model.TipoEndereco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CadastroRepositoryTests {

    @Autowired
    private CadastroRepository underTest;

    @Test
    void verificarSeUmClienteExistePeloEmail(){

        // given
        Endereco endereco = new Endereco(
                "Rua 25",
                "A42","",
                "25735-200",
                "Espaço",
                "DeathStar",
                "Galatico",
                TipoEndereco.TRABALHO
        );

        String email = "anakin_gatin@gmail.com";
        ClienteCadastroDTO cadastroDTO = new ClienteCadastroDTO(
                email,
                "D34Th-5T4R",
                "DarthVader",
                "82581109009",
                "300807661",
                25000.0,
                (List<Endereco>) endereco
        );

        // when
        Boolean esperado = Objects.equals(underTest.findByEmail(email).getEmail(), cadastroDTO.getEmail());

        // then
        assertThat(esperado).isTrue();

    }

}

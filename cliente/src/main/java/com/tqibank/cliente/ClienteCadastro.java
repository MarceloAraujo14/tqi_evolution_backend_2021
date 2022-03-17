package com.tqibank.cliente;


import com.tqibank.cliente.endereco.Endereco;

import java.util.List;

public record ClienteCadastro(
        String email,
        String senha,
        String nome,
        String cpf,
        String rg,
        Double renda,
        List<Endereco> enderecos){

}

package com.tqibank.cliente.request;

import com.tqibank.cliente.endereco.Endereco;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class RetornoRequest {

    private String email;

    private String nome;

    private String cpf;

    private String rg;

    private BigDecimal renda;

    private List<Endereco> enderecos = new ArrayList<>(3);


    @Override
    public String toString() {
        return "Cliente{" +
                "email: '" + email + '\'' +
                ", nome: '" + nome + '\'' +
                ", cpf: '" + cpf + '\'' +
                ", rg: '" + rg + '\'' +
                ", renda: " + renda +
                ", enderecos: " + enderecos +
                '}';
    }

}

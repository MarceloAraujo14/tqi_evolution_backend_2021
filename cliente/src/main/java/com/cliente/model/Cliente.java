package com.cliente.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;


@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("cliente")
@Document(indexName = "clientes")
public class Cliente {

    @Id
    private String id;

    private Status status;

    private String email;

    private String senha;

    private String nome;

    private String cpf;

    private String rg;

    private Double renda;

    private List<Endereco> enderecos;


    public void setEnderecos(List<Endereco> endereco) {

        this.enderecos = endereco;

    }


}

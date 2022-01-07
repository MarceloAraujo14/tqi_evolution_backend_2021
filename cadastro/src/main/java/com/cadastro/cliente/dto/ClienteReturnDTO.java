package com.cadastro.cliente.dto;

import com.cadastro.cliente.model.Endereco;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data @Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "clientes")
public class ClienteReturnDTO {

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

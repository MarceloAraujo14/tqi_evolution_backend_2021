package com.cliente.dto;

import com.cliente.model.Endereco;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteReturnDTO {

    private String email;

    private String senha;

    private String nome;

    private String cpf;

    private String rg;

    private Double renda;

    private List<Endereco> enderecos;

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
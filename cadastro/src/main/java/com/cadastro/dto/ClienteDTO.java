package com.cadastro.dto;

import com.cadastro.model.Endereco;
import com.validation.constraints.Nome;
import com.validation.constraints.RG;
import com.validation.constraints.Senha;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "clientes")
public class ClienteDTO {

    @NotBlank(message = "O campo email não pode estar em branco.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "O campo senha não pode estar em branco.")
    @Senha
    private String senha;

    @NotBlank(message = "O campo nome não pode estar em branco.")
    @Length(min = 6, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    @Nome
    private String nome;

    @NotBlank(message = "O campo CPF não pode estar em branco.")
    @CPF(message = "CPF inválido.")
    private String cpf;

    @NotBlank(message = "O campo RG não pode estar em branco.")
    @RG
    private String rg;

    @NotNull(message = "O campo não pode estar em branco.")
    private Double renda;

    @Valid
    private List<Endereco> enderecos;

    public void setEnderecos(@Valid @RequestBody List<Endereco> endereco) {

        this.enderecos = endereco;

    }

}


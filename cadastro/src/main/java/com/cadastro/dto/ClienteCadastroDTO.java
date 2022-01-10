package com.cadastro.dto;

import com.cadastro.model.Endereco;
import com.validation.constraints.*;
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
public class ClienteCadastroDTO {

    @NotBlank(message = "O campo email não pode estar em branco.")
    @Email(message = "Email inválido.")
    @DuplicatedEmail
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
    @DuplicatedCPF
    private String cpf;

    @NotBlank(message = "O campo RG não pode estar em branco.")
    @RG
    @DuplicatedRG
    private String rg;

    @NotNull(message = "O campo não pode estar em branco.")
    private Double renda;

    private List<Endereco> enderecos;

    public void setEnderecos(List<Endereco> endereco) {

        this.enderecos = endereco;

    }

}

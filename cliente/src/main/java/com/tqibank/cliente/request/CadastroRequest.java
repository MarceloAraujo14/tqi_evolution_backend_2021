package com.tqibank.cliente.request;

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CadastroRequest {

    @NotBlank(message = "O campo não pode estar em branco.")
    @Email(message = "Email inválido.")
    @DuplicatedEmail
    private String email;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Senha
    private String senha;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Length(min = 6, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    @Nome
    private String nome;

    @NotBlank(message = "O campo não pode estar em branco.")
    @CPF(message = "CPF inválido.")
    @DuplicatedCPF
    private String cpf;

    @NotBlank(message = "O campo não pode estar em branco.")
    @RG
    @DuplicatedRG
    private String rg;

    @NotNull(message = "O campo não pode estar em branco.")
    private BigDecimal renda;

    @Valid
    private List<Endereco> enderecos = new ArrayList<>(3);


    @Override
    public String toString() {
        return "Cliente{" +
                "email: '" + email + '\'' +
                ", senha: '" + senha + '\'' +
                ", nome: '" + nome + '\'' +
                ", cpf: '" + cpf + '\'' +
                ", rg: '" + rg + '\'' +
                ", renda: " + renda +
                ", enderecos: " + enderecos +
                '}';
    }

}

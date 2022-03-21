package com.tqibank.cliente;

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.validation.constraints.*;
import lombok.AllArgsConstructor;
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
public class ClienteRequest {

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

    public Cliente clienteCadastroToCliente(ClienteRequest clienteRequest){
        Cliente cliente = Cliente.builder()
                .email(clienteRequest.getEmail())
                .nome(clienteRequest.getNome())
                .cpf(clienteRequest.getCpf())
                .rg(clienteRequest.getRg())
                .senha(clienteRequest.getSenha())
                .renda(clienteRequest.getRenda())
                .enderecos(clienteRequest.getEnderecos())
                .build();

        cliente.getEnderecos().get(0).setCliente(cliente);

        return cliente;
    }




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

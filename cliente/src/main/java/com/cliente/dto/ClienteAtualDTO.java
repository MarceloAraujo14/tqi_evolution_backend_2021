package com.cliente.dto;

import com.cliente.model.Endereco;
import com.validation.constraints.Nome;
import com.validation.constraints.Senha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAtualDTO {

    @NotBlank(message = "O campo senha não pode estar em branco.")
    @Senha
    private String senha;

    @NotBlank(message = "O campo nome não pode estar em branco.")
    @Length(min = 6, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    @Nome
    private String nome;

    @NotNull(message = "O campo não pode estar em branco.")
    private Double renda;

    @Valid
    private List<Endereco> enderecos;

    public void setEnderecos(@Valid @RequestBody List<Endereco> endereco) {

        this.enderecos = endereco;

    }

}

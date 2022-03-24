package com.tqibank.cliente.request;

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.validation.constraints.Nome;
import com.tqibank.validation.constraints.Senha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AtualizacaoRequest {

    @NotBlank(message = "O campo não pode estar em branco.")
    @Senha
    private String senha;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Length(min = 6, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    @Nome
    private String nome;

    @NotNull(message = "O campo não pode estar em branco.")
    private BigDecimal renda;

    @Valid
    private List<Endereco> enderecos = new ArrayList<>(3);

}

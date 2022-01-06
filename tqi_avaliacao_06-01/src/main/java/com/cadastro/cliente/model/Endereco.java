package com.cadastro.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @NotBlank(message = "O campo não pode estar em branco.")
    private String endereco;

    @NotBlank(message = "O campo não pode estar em branco.")
    private String numero;

    private String complemento;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido.")
    private String cep;

    @NotBlank(message = "O campo não pode estar em branco.")
    private String bairro;

    @NotBlank(message = "O campo não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "O campo não pode estar em branco.")
    private String estado;


    @Id
    private TipoEndereco tipo;
}

package com.tqibank.cliente.endereco;

import com.tqibank.cliente.Cliente;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "enderecos")
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String logradouro;

//    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String numero;


    private String complemento;

//    @NotBlank(message = "O campo não pode estar em branco.")
//    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido.")
    @Column(nullable = false)
    private String cep;

//    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String bairro;

//    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String cidade;

//    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private tipoEndereco tipo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_email", referencedColumnName = "email",nullable = false)
    private Cliente cliente;

    public Endereco(String logradouro, String numero, String complemento, String cep, String bairro, String cidade,
                    String estado, tipoEndereco tipo, Cliente cliente) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo=" + tipo +
                ", cliente=" + cliente +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(numero, endereco.numero) && Objects.equals(complemento, endereco.complemento) && Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, complemento, cep);
    }
}

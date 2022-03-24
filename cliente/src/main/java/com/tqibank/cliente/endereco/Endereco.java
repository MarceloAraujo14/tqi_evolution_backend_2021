package com.tqibank.cliente.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tqibank.cliente.Cliente;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "enderecos")
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id", nullable = false)
    private Long id;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido.")
    @Column(nullable = false)
    private String cep;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String bairro;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    private String estado;

    @NotNull(message = "O campo não pode estar em branco.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private tipoEndereco tipo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_email", referencedColumnName = "email",nullable = false)
    @ToString.Exclude
    private Cliente cliente;



    public Endereco(String logradouro, String numero, String complemento, String cep, String bairro, String cidade,
                    String estado, tipoEndereco tipo) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.tipo = tipo;
    }

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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Endereco endereco = (Endereco) o;
        return id != null && Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

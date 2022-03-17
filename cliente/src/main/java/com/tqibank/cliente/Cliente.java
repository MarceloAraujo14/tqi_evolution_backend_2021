package com.tqibank.cliente;

import com.tqibank.cliente.endereco.Endereco;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "clientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

    @Id
    @Column(unique = true)
    private String email;

    private String senha;

    private String nome;

    private String cpf;

    private String rg;

    private Double renda;

    @OneToMany(mappedBy = "cliente", targetEntity = Endereco.class, cascade = CascadeType.ALL)
    private List<Endereco> enderecos;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return email != null && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

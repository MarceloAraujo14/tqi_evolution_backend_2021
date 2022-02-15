package com.cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ProxyUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Getter @Setter
@ToString
@NoArgsConstructor
@Entity(name = "cliente")
@Table(name = "clientes")
public class Cliente implements UserDetails {


    @Autowired
    private List<? extends GrantedAuthority> grantedAuthorities;

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    private String senha;

    private String nome;

    private String cpf;

    private String rg;

    private Double renda;

    private List<Endereco> enderecos;

    private UsuarioRole usuarioRole;

    private Boolean locked = false;

    private Boolean enable = false;


    public Cliente(List<? extends GrantedAuthority> grantedAuthorities,
            String email,
            String senha,
            String nome,
            String cpf,
            String rg,
            Double renda,
            List<Endereco> enderecos,
            UsuarioRole usuarioRole,
            Boolean locked,
            Boolean enable) {
        this.grantedAuthorities = grantedAuthorities;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.renda = renda;
        this.enderecos = enderecos;
        this.usuarioRole = usuarioRole;
        this.locked = locked;
        this.enable = enable;
    }

    public void setEnderecos(List<Endereco> endereco) {

        this.enderecos = endereco;

    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuarioRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o))
            return false;
        Cliente cliente = (Cliente) o;
        return email != null && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

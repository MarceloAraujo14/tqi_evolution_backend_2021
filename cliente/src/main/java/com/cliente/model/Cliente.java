package com.cliente.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@TypeAlias("cliente")
@Document(indexName = "clientes")
public class Cliente implements UserDetails {

    @Autowired
    private List<? extends GrantedAuthority> grantedAuthorities;


    @Id
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


    public void setEnderecos(List<Endereco> endereco) {

        this.enderecos = endereco;

    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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




}

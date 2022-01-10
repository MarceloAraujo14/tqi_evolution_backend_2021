package com.cliente.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("cliente")
@Document(indexName = "clientes")
public class Cliente implements UserDetails {

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




}

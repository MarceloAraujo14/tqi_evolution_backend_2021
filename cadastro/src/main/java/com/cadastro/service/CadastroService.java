package com.cadastro.service;

import com.cadastro.model.Cliente;
import com.cadastro.model.UsuarioRole;
import com.cadastro.repository.CadastroRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroService {


    private final CadastroRepository cadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public String cadastro(Cliente cliente) {

            cliente.setUsuarioRole(UsuarioRole.CLIENTE);
            cliente.setEnable(true);
            cliente.setLocked(true);
            cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
            cadastroRepository.save(cliente);
            return "{\"Cadastro realizado com sucesso.\"}";

    }




}

package com.cadastro.service;

import com.cadastro.model.Cliente;
import com.cadastro.model.UsuarioRole;
import com.cadastro.repository.CadastroRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CadastroService {

    private final ModelMapper mapper;

    private final CadastroRepository cadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public String cadastro(Cliente cliente) {

        if(cadastroRepository.findByEmail(cliente.getEmail()) != null){
            return "{\"email\":\"Email já cadastrado.\"}";
        }
        if(cadastroRepository.findByCpf(cliente.getCpf()).isPresent()){
            return "{\"cpf:\":\"CPF já cadastrado.\"}";
        }else if (cadastroRepository.findByRg(cliente.getRg()).isPresent()){
            return "{\"rg\":\"RG já cadastrado\"}";
        }else {
            cliente.setUsuarioRole(UsuarioRole.CLIENTE);
            cliente.setEnable(true);
            cliente.setLocked(true);
            cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
            cadastroRepository.save(cliente);
            return "{\"Cadastro realizado com sucesso.\"}";
        }

    }


    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}

package com.cadastro.service;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Cliente;
import com.cadastro.model.UsuarioRole;
import com.cadastro.repository.CadastroRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CadastroService {

    @Autowired
    private final ModelMapper mapper;
    @Autowired
    private final CadastroRepository cadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity cadastro(ClienteCadastroDTO clienteDTO) {
        try {Cliente cliente = mapper.map(clienteDTO, Cliente.class);
            cliente.setUsuarioRole(UsuarioRole.CLIENTE);
            cliente.setEnable(true);
            cliente.setLocked(true);
            cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
            cadastroRepository.save(cliente);
            return ResponseEntity.ok("{\"Cadastro realizado com sucesso.\"}");

        } catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.badRequest().body("{\"Não foi possível realizar o cadastro.\"}");
        }


    }

    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }




}

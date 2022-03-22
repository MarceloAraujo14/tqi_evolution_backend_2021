package com.tqibank.cliente;

import com.tqibank.exceptions.DuplicatedEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> cadastrarCliente(Cliente cliente) {
        try{
            if(EmailJaCadastrado(cliente)){
                throw new DuplicatedEmailException(String.format("Email %s já cadastrado.", cliente.getEmail()));
            }
        repository.save(cliente);
        return ResponseEntity.ok().body("{\"Cliente cadastrado com sucesso.\"}");
        }
        catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.badRequest().body("{\"Não foi possível realizar a operação de cadastro.\"}");
        } catch (DuplicatedEmailException e) {
            return ResponseEntity.badRequest().body(String.format("Email %s já cadastrado.", cliente.getEmail()));
        }
    }

    public Optional<Cliente> encontrarClientePorEmail(String email) {
        return repository.findById(email);
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

    public boolean EmailJaCadastrado(Cliente cliente){
        return repository.findById(cliente.getEmail()).isPresent();
    }
}

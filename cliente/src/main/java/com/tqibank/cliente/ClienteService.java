package com.tqibank.cliente;

import com.tqibank.cliente.request.AtualizacaoRequest;
import com.tqibank.cliente.request.RetornoRequest;
import com.tqibank.exceptions.DuplicatedEmailException;
import com.tqibank.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

//    public ResponseEntity<List<RetornoRequest>> listarClientes() {
//        List<RetornoRequest> clientes = repository.findAll();
//        return ResponseEntity.ok().body(repository.findAll());
//    }

    public ResponseEntity<String> cadastrarCliente(Cliente cliente) {
        try{
            if(repository.existsById(cliente.getEmail())){
                throw new DuplicatedEmailException(
                        String.format("{\"Email %s já cadastrado.\"}", cliente.getEmail()));
            }
        repository.save(cliente);

        return ResponseEntity.ok().body("{\"Cliente cadastrado com sucesso.\"}");
        }

        catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.badRequest()
                    .body("{\"Não foi possível realizar a operação de cadastro.\"}");
        }

        catch (DuplicatedEmailException e) {
            return ResponseEntity.badRequest()
                    .body(String.format("{\"Email %s já cadastrado.\"}", cliente.getEmail()));
        }
    }

    public ResponseEntity<String> encontrarClientePorEmail(String email) {
        if(repository.existsById(email)){
            return ResponseEntity.ok().body(repository.findById(email).get().toString());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Cliente com o email" + email + " não cadastrado.\"}");
        }
    }

    public ResponseEntity<String> encontrarClientePorCpf(String cpf) {
        if(repository.existsByCpf(cpf)){
            return ResponseEntity.ok().body(repository.findByCpf(cpf).get().toString());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Cliente com o cpf: " + cpf + " não cadastrado.\"}");
        }
    }

    public ResponseEntity<String> encontrarClientePorRg(String rg) {
        if(repository.existsByRg(rg)){
            return ResponseEntity.ok().body(repository.findByRg(rg).get().toString());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Cliente com o rg: " + rg + " não cadastrado.\"}");
        }
    }

    public ResponseEntity<String> atualizarCliente(AtualizacaoRequest request, String email){
        if (repository.existsById(email)){

            Cliente clienteAntigo = repository.findById(email).get();

            Cliente clienteNovo = Mapper.atualizacaoRequestToCliente(request, clienteAntigo);

            repository.save(clienteNovo);

            return ResponseEntity.accepted().body("{\"Cadastro atualizado com sucesso.\"}");
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"Cliente com o email" + email + " não cadastrado.\"}");

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

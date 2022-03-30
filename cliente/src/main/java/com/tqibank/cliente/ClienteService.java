package com.tqibank.cliente;

import com.tqibank.cliente.dto.UpdateRequest;
import com.tqibank.cliente.dto.ClienteResponse;
import com.tqibank.exceptions.ClienteNotFoundException;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository repository;

    private final Mapper mapper;

    @Autowired
    public ClienteService(ClienteRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseEntity<List<ClienteResponse>> listarClientes() {

        return ResponseEntity.ok().body(mapper.toListResponse(repository.findAll()));
    }

    public ResponseEntity<String> cadastrarCliente(Cliente cliente) throws DuplicatedEmailException {
        if (repository.existsById(cliente.getEmail())) {
            throw new DuplicatedEmailException(
                    String.format("{\"Email %s já cadastrado.\"}", cliente.getEmail()));
        }
        repository.save(cliente);
        return ResponseEntity.ok().body("{\"Cliente cadastrado com sucesso.\"}");
    }

    public ResponseEntity<String> encontrarClientePorCpf(String cpf) {
        if(repository.existsByCpf(cpf) && repository.findByCpf(cpf).isPresent()){
            return ResponseEntity.ok().body(repository.findByCpf(cpf).get().toString());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Cliente com o cpf: " + cpf + " não cadastrado.\"}");
        }
    }

    public ResponseEntity<ClienteResponse> encontrarClientePorRg(String rg) throws ClienteNotFoundException {
            ClienteResponse clienteResponse = mapper
                    .toClienteResponse(repository.findByRg(rg)
                            .orElseThrow(() -> new ClienteNotFoundException(rg)));

            return ResponseEntity.ok().body(clienteResponse);
    }

    public ResponseEntity<String> atualizarCliente(UpdateRequest request, String email){
        if (repository.existsById(email)){

            Cliente clienteNovo = mapper.updateToCliente(request);
            clienteNovo.setEmail(email);
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

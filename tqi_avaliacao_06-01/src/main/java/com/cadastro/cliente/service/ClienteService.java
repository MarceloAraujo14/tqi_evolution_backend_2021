package com.cadastro.cliente.service;

import com.cadastro.cliente.dto.ClienteDTO;
import com.cadastro.cliente.model.Cliente;
import com.cadastro.cliente.model.Status;
import com.cadastro.cliente.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public String cadastro(Cliente cliente) {

        if(clienteRepository.findByEmail(cliente.getEmail()).isPresent()){
            return "Email já cadastrado";
        }
        if(clienteRepository.findByCpf(cliente.getCpf()).isPresent()){
            return "CPF já cadastrado";
        }else if (clienteRepository.findByRg(cliente.getRg()).isPresent()){
            return "RG já cadastrado";
        }else {
            cliente.setStatus(Status.ATIVO);
            clienteRepository.save(cliente);
            return cliente.toString();
        }

    }

    public String update(Cliente clienteNovo, String email) {
        Optional<Cliente> clienteAntigo = clienteRepository.findByEmail(email);
        if(clienteAntigo.isPresent() && clienteAntigo.get().getStatus() == Status.ATIVO){
            clienteNovo.setId(clienteAntigo.get().getId());
            clienteNovo.setStatus(Status.ATIVO);
            clienteRepository.save(clienteNovo);
            return "Dados atualizados com sucesso.";
        }else {

        return "Cliente não encontrado";}
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

    public String delete() {
        clienteRepository.deleteAll();
        return "Deletado com sucesso";
    }

    public Iterable<Cliente> findAll() {

        return clienteRepository.findAll();
    }

}

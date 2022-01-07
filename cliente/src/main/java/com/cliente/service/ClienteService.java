package com.cliente.service;

import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.cliente.model.Status;
import com.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteReturnDTO findByEmail(String email) {

        return mapper.map(clienteRepository.findByEmail(email), ClienteReturnDTO.class);
    }

    public String cadastro(Cliente cliente) {

        if(clienteRepository.findByEmail(cliente.getEmail()) != null){
            return "Email já cadastrado";
        }
        if(clienteRepository.findByCpf(cliente.getCpf()).isPresent()){
            return "CPF já cadastrado";
        }else if (clienteRepository.findByRg(cliente.getRg()).isPresent()){
            return "RG já cadastrado";
        }else {
            cliente.setStatus(Status.ATIVO);
            clienteRepository.save(cliente);
            return "Cadastro realizado com sucesso.";
        }

    }

    public String update(Cliente clienteNovo, String email) {
        Cliente clienteAntigo = clienteRepository.findByEmail(email);

        if(clienteAntigo.getStatus() == Status.ATIVO){
            clienteNovo.setId(clienteAntigo.getId());
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

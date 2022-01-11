package com.service;

import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.cliente.model.UsuarioRole;
import com.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ClienteService implements UserDetailsService {

    private final ModelMapper mapper;

    private final ClienteRepository clienteRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clienteRepository.findById(email).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado"));
    }


    public ClienteReturnDTO findByEmail(String email) {

        return mapper.map(clienteRepository.findByEmail(email), ClienteReturnDTO.class);
    }



    public String update(Cliente clienteNovo, String email) {
        Cliente clienteAntigo = clienteRepository.findByEmail(email);
        if(clienteAntigo.isEnabled() && clienteAntigo.isAccountNonLocked()){
            clienteNovo.setEmail(email);
            clienteNovo.setUsuarioRole(UsuarioRole.CLIENTE);
            clienteNovo.setEnable(true);
            clienteNovo.setLocked(true);
            clienteNovo.setSenha(passwordEncoder.encode(clienteNovo.getSenha()));

            clienteRepository.save(clienteNovo);
            return "{\"Dados atualizados com sucesso.\"}";
        }else {

        return "{\"Cliente não encontrado\"}";}
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

    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        for (Cliente cliente: clienteRepository.findAll()
             ) {
            clientes.add(cliente);
        }

        return clientes;
    }


}

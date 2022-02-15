package com.service;

import com.cliente.dto.ClienteAtualDTO;
import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.cliente.model.Endereco;
import com.cliente.model.UsuarioRole;
import com.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

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

    public Cliente findById(String email) {
            return clienteRepository.findById(email).get();
    }

    public ResponseEntity findById(Cliente user, String email) {
        if(Objects.equals(user.getEmail(), email)){
            return ResponseEntity.ok(mapper.map(clienteRepository.findById(email), ClienteReturnDTO.class).toString());}
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Usuário não tem permissão para realizar essa requisição\"");}
    }

    public Endereco encontrarEndereco(String email){
        return clienteRepository.findById(email).get().getEnderecos().get(0);
    }


    public ResponseEntity update(ClienteAtualDTO clienteDTO, Cliente user, String email) {

            if(Objects.equals(user.getEmail(), email)) {
                Cliente clienteNovo = mapper.map(clienteDTO, Cliente.class);

                Cliente clienteAntigo = clienteRepository.findById(email).get();
                if (clienteAntigo.isEnabled() && clienteAntigo.isAccountNonLocked()) {
                    clienteNovo.setEmail(email);
                    clienteNovo.setUsuarioRole(UsuarioRole.CLIENTE);
                    clienteNovo.setEnable(true);
                    clienteNovo.setLocked(true);
                    clienteNovo.setSenha(passwordEncoder.encode(clienteNovo.getSenha()));

                    clienteRepository.save(clienteNovo);
                    return ResponseEntity.ok().body("{\"Dados atualizados com sucesso.\"}");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Cliente não encontrado\"}");
                }
            }
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Usuário não tem permissão para realizar essa requisição\"");
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

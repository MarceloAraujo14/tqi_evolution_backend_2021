package com.cadastro.cliente.controller;

import com.cadastro.cliente.dto.ClienteCadastroDTO;
import com.cadastro.cliente.dto.ClienteDTO;
import com.cadastro.cliente.dto.ClienteReturnDTO;
import com.cadastro.cliente.model.Cliente;
import com.cadastro.cliente.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ModelMapper mapper;



    @GetMapping(value = "/{email}")
    public ResponseEntity<ClienteReturnDTO> getClientByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(clienteService.findByEmail(email));
    }



    @PostMapping(value = "/cadastrar")
    public ResponseEntity<String> cadastro(@Valid @RequestBody ClienteCadastroDTO clienteDTO){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.cadastro(cliente));
    }



    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> update( @PathVariable("email") String email,@Valid  @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.update(cliente, email));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(clienteService.handleException(ex));
    }

    //Metodos não utilizados pela interface

    @DeleteMapping(value = "/del")
    public ResponseEntity<String> delete(){
        return ResponseEntity.ok(clienteService.delete());
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Cliente>> getAllClientes(){

        return ResponseEntity.ok(clienteService.findAll());
    }

}

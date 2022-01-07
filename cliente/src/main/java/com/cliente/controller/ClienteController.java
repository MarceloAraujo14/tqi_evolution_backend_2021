package com.cliente.controller;

import com.cliente.dto.ClienteDTO;
import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final ModelMapper mapper;



    @GetMapping(value = "/{email}")
    public ResponseEntity<ClienteReturnDTO> getClientByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(clienteService.findByEmail(email));
    }

    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> update( @PathVariable("email") String email,@Valid  @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.update(cliente, email));
    }





    //
    //
    //
    //


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

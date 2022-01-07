package com.cadastro.controller;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Cliente;
import com.cadastro.service.ClienteService;
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
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<String> cadastro(@Valid @RequestBody ClienteCadastroDTO clienteDTO){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.cadastro(cliente));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(clienteService.handleException(ex));
    }

    //Metodos não utilizados pela interface

}

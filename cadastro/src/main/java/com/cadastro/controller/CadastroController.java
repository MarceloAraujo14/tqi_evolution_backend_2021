package com.cadastro.controller;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Cliente;
import com.cadastro.service.CadastroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CadastroController {

    @Autowired
    private final CadastroService cadastroService;


    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastro(@Valid @RequestBody ClienteCadastroDTO clienteDTO){

        return ResponseEntity.ok(cadastroService.cadastro(clienteDTO));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(cadastroService.handleException(ex));
    }





}
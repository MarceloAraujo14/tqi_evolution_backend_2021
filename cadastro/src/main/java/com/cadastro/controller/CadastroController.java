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
    @Autowired
    private final ModelMapper mapper;

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastro(@Valid @RequestBody ClienteCadastroDTO clienteDTO){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(cadastroService.cadastro(cliente));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(cadastroService.handleException(ex));
    }





}
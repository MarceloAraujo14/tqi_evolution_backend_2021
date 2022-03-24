package com.tqibank.cliente;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes/")
@Api(value = "TQI API REST")
@CrossOrigin("*")
public class ClienteController {

   private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @ApiOperation(value = "Cadastro de clientes")
    @PostMapping(value = "/cadastro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid ClienteRequest cliente){
        log.info("novo cadastro de cliente {}", cliente);

        return clienteService.cadastrarCliente(cliente.clienteCadastroToCliente(cliente));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        log.info(clienteService.handleException(ex).toString());
        return clienteService.handleException(ex);
    }



}

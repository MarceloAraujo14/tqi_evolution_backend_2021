package com.tqibank.cliente;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import com.tqibank.cliente.dto.UpdateRequest;
import com.tqibank.cliente.dto.RegistrationRequest;
import com.tqibank.cliente.dto.ClienteResponse;
import com.tqibank.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

   private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Retorna a lista de todos os clientes cadastrados.")
    @GetMapping(value = "/clientes")
    public ResponseEntity<List<ClienteResponse>> listarClientes(){
        return clienteService.listarClientes();
    }

    @Operation(summary = "Cadastra um cliente. ")
    @PostMapping(value = "/cliente/cadastro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid RegistrationRequest request){

        log.info("novo cadastro de cliente {}", request);

        return clienteService.cadastrarCliente(Mapper.toCliente(request));
    }

    @Operation(summary = "Retorna um cliente pelo email. ")
    @GetMapping(value = "/cliente/{email}")
    public ResponseEntity<String> retornarClientePorEmail(@PathVariable(value = "email") String email){

        return clienteService.encontrarClientePorEmail(email);
    }

    @Operation(summary = "Retorna um cliente pelo cpf. ")
    @GetMapping(value = "/cliente/cpf/{cpf}")
    public ResponseEntity<String> retornarClientePorCPF(@PathVariable(value = "cpf") String cpf){

        return clienteService.encontrarClientePorCpf(cpf);
    }

    @Operation(summary = "Retorna um cliente pelo rg. ")
    @GetMapping(value = "/cliente/rg/{rg}")
    public ResponseEntity<String> retornarClientePorRg(@PathVariable(value = "rg") String rg){

        return clienteService.encontrarClientePorRg(rg);
    }

    @Operation(summary = "Atualiza os dados de um cliente. ")
    @PostMapping(value = "/cliente/{email}")
    public ResponseEntity<String> atualizarCliente(@Valid @RequestBody UpdateRequest request, @PathVariable(value = "email") String email){

        return clienteService.atualizarCliente(request, email);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        log.info(clienteService.handleException(ex).toString());
        return clienteService.handleException(ex);
    }



}

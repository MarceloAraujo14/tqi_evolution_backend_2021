package com.tqibank.cliente;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes/")
public record ClienteController(ClienteService clienteService) {

    @PostMapping("/cadastro")
    public void cadastrarCliente(@RequestBody ClienteCadastro clienteCadastro){
        log.info("novo cadastro de cliente {}", clienteCadastro);
        clienteService.cadastrarCliente(clienteCadastro);
    }
}

package com.tqibank.cliente;

import org.springframework.stereotype.Service;

@Service
public record ClienteService(ClienteRepository repository) {

    public void cadastrarCliente(ClienteCadastro clienteCadastro) {

        Cliente cliente = Cliente.builder()
                .email(clienteCadastro.email())
                .nome(clienteCadastro.nome())
                .cpf(clienteCadastro.cpf())
                .rg(clienteCadastro.rg())
                .senha(clienteCadastro.senha())
                .renda(clienteCadastro.renda())
                .enderecos(clienteCadastro.enderecos())
                .build();

        clienteCadastro.enderecos().get(0).setCliente(cliente);

        cliente.setEnderecos(clienteCadastro.enderecos());

        repository.save(cliente);

        //todo: validar os campos
    }
}

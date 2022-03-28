package com.tqibank.mapper;

import com.tqibank.cliente.Cliente;
import com.tqibank.cliente.request.AtualizacaoRequest;
import com.tqibank.cliente.request.CadastroRequest;
import com.tqibank.cliente.request.RetornoRequest;

import java.util.Optional;

public class Mapper {

    private Mapper() {
    }

    public static Cliente requestToCliente(CadastroRequest cadastroRequest){

        Cliente cliente = Cliente.builder()
                .email(cadastroRequest.getEmail())
                .nome(cadastroRequest.getNome())
                .cpf(cadastroRequest.getCpf())
                .rg(cadastroRequest.getRg())
                .senha(cadastroRequest.getSenha())
                .renda(cadastroRequest.getRenda())
                .enderecos(cadastroRequest.getEnderecos())
                .build();

        cliente.getEnderecos().get(0).setCliente(cliente);

        return cliente;
    }



    public static Cliente atualizacaoRequestToCliente(AtualizacaoRequest request, Cliente clienteAntigo) {

        Cliente cliente = Cliente.builder()
                .email(clienteAntigo.getEmail())
                .nome(request.getNome())
                .cpf(clienteAntigo.getCpf())
                .rg(clienteAntigo.getRg())
                .senha(request.getSenha())
                .renda(request.getRenda())
                .enderecos(clienteAntigo.getEnderecos())
                .build();

        cliente.getEnderecos().get(0).setCliente(cliente);

        return cliente;
    }

    public static RetornoRequest clienteToRetornoRequest(Cliente cliente){
        return RetornoRequest.builder()
                .email(cliente.getEmail())
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .rg(cliente.getRg())
                .renda(cliente.getRenda())
                .enderecos(cliente.getEnderecos())
                .build();
    }
}
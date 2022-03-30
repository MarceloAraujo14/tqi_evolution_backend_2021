package com.tqibank.mapper;

import com.tqibank.cliente.Cliente;
import com.tqibank.cliente.dto.UpdateRequest;
import com.tqibank.cliente.dto.RegistrationRequest;
import com.tqibank.cliente.dto.ClienteResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class Mapper {

    @Autowired
    private ModelMapper mapper;

    public Cliente toCliente(RegistrationRequest registrationRequest){
        return mapper.map(registrationRequest, Cliente.class);
    }

    public Cliente updateToCliente(UpdateRequest request) {
        return mapper.map(request, Cliente.class);
    }

    public ClienteResponse toClienteResponse(Cliente cliente){
        return mapper.map(cliente, ClienteResponse.class);
    }

    public List<ClienteResponse> toListResponse(List<Cliente> clientes){
        return clientes.stream().map(this::toClienteResponse).collect(Collectors.toList());
    }
}

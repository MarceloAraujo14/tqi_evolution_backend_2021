package com.tqibank.cliente;

/*
-Testes unitários
    O que deve ser testado:
    - Salvar um cliente
    - Não salvar um cliente com dado obrigatório faltando;
    - Não salvar um cliente com email repetido;
    - Não salvar um cliente com cpf repetido;
    - Não salvar um cliente com rg repetido;
 */

import com.tqibank.cliente.endereco.Endereco;
import com.tqibank.cliente.endereco.tipoEndereco;
import com.tqibank.exceptions.DuplicatedEmailException;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ClienteService.class})
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;


    @Test
    @Name("Deveria Salvar Um Cliente")
    void cadastrarCliente(){
        //given
        Cliente cliente = getCliente();
        //when
        when(repository.save((Cliente) any())).thenReturn(cliente);
        ResponseEntity<String> result = this.clienteService.cadastrarCliente(cliente);
        ResponseEntity<String> expected = ResponseEntity.ok().body("{\"Cliente cadastrado com sucesso.\"}");
        //then

        assertThat(result).isEqualTo(expected);

        verify(this.repository).save((Cliente) any());

    }

    @Test
    @Name("Deveria Retornar Erro Ao Tentar Salvar Um Cliente Sem Email")
    void cadastrarCliente2() throws HttpClientErrorException.BadRequest{
        //given

        Cliente cliente = getCliente();
        cliente.setEmail(null);
        //when
        lenient().when(repository.save(cliente)).thenThrow(HttpClientErrorException.BadRequest.class);

        ResponseEntity<String> expected = ResponseEntity.badRequest().body("{\"Não foi possível realizar a operação de cadastro.\"}");
        ResponseEntity<String> result = clienteService.cadastrarCliente(cliente);

        assertThat(result).isEqualTo(expected);
        verify(repository,times(1)).save(cliente);


    }

    @Test
    @Name("Não salvar um cliente com email repetido")
    void cadastrarCliente3() throws DuplicatedEmailException {
        //given
        Cliente clienteAntigo = getCliente();
        Cliente clienteNovo = getCliente();

        //when
        lenient().when(repository.findById(clienteNovo.getEmail())).thenReturn(Optional.of(clienteAntigo));
        clienteService.cadastrarCliente(clienteNovo);
        //then
        assertThatThrownBy(() -> clienteService.cadastrarCliente(clienteNovo))
                .hasMessageContaining(String.format("Email %s já está cadastrado.",clienteNovo.getEmail()))
                .isInstanceOf(DuplicatedEmailException.class);

        verify(repository, times(1)).findById(clienteNovo.getEmail());

    }
    //todo: testar esse metodo

    public Cliente getCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome("Jhon Doe");
        cliente.setEmail("jhon.doe@gmail.com");
        cliente.setSenha("Abc/12345678@");
        cliente.setCpf("35096343065");
        cliente.setRg("403829264");
        cliente.setRenda(new BigDecimal("5.000"));
        cliente.setEnderecos(
                List.of( new Endereco("Rua A", "52A", "", "57237970", "Teste",
                        "Teste", "Teste", tipoEndereco.RESIDENCIAL)));

        cliente.setEnderecos(List.of(new Endereco("Rua A", "52A", "", "57237970", "Teste",
                "Teste", "Teste", tipoEndereco.RESIDENCIAL,cliente)));

        return cliente;
    }

}
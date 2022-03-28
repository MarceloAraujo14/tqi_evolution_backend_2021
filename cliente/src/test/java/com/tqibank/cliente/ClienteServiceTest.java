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
import com.tqibank.cliente.request.AtualizacaoRequest;
import com.tqibank.exceptions.DuplicatedEmailException;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ClienteService.class})
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

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
        String email = "jhon.doe@gmail.com";

        //when
        when(repository.existsById(email)).thenReturn(true);
        ResponseEntity<String> expected = ResponseEntity.badRequest().body(String.format("{\"Email %s já cadastrado.\"}", email));
        ResponseEntity<String> result = clienteService.cadastrarCliente(clienteNovo);
        //then

        assertThat(result).isEqualTo(expected);
        verify(repository, times(1)).existsById(email);
        verify(repository,times(0)).save(clienteNovo);

    }


    @Test
    @Name("Deveria retornar um cliente cadastrado pelo email")
    void encontrarClientePorEmail() {
        //given
        String email = "jhon.doe@gmail.com";
        Cliente cliente = getCliente();
        //when
        when(repository.findById(email)).thenReturn(Optional.of(cliente));
        when(repository.existsById(email)).thenReturn(true);

        //then
        assertThat(clienteService.encontrarClientePorEmail(email)).isNotNull();
        verify(repository,times(1)).findById(email);
        verify(repository,times(1)).existsById(email);
    }

    @Test
    @Name("Deveria retornar mensagem de cliente não encontrado ao buscar um email não existente")
    void encontrarClientePorEmail2() {
        //given
        String email = "jhon.doe@gmail.com";
        Cliente cliente = getCliente();
        //when
        when(repository.existsById(email)).thenReturn(false);

        ResponseEntity<String> result = clienteService.encontrarClientePorEmail(email);
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"Cliente com o email" + email + " não cadastrado.\"}");
        
        //then
        assertThat(result).isEqualTo(expected);
        verify(repository,times(1)).existsById(email);
    }


    @Test
    void encontrarClientePorCpf() {
        //given
        String cpf = "35096343065";
        Cliente cliente = getCliente();
        //when
        when(repository.findByCpf(cpf)).thenReturn(Optional.of(cliente));
        when(repository.existsByCpf(cpf)).thenReturn(true);

        //then
        assertThat(clienteService.encontrarClientePorCpf(cpf)).isNotNull();
        verify(repository,times(1)).findByCpf(cpf);
        verify(repository,times(1)).existsByCpf(cpf);
    }

    @Test
    void encontrarClientePorRg() {
        //given
        String rg = "403829264";
        Cliente cliente = getCliente();
        //when
        when(repository.findByRg(rg)).thenReturn(Optional.of(cliente));
        when(repository.existsByRg(rg)).thenReturn(true);

        //then
        assertThat(clienteService.encontrarClientePorRg(rg)).isNotNull();
        verify(repository,times(1)).findByRg(rg);
        verify(repository,times(1)).existsByRg(rg);

    }

    @Test
    void atualizarCliente() {

        //given

        String email = "jhon.doe@gmail.com";

        AtualizacaoRequest clienteNovo = AtualizacaoRequest.builder()
                .nome("Jhon Doe")
                .senha("Abc/12345678@")
                .renda(new BigDecimal("5.000"))
                .enderecos(List.of(
                        new Endereco(
                                "Rua A",
                                "52A",
                                "",
                                "57237970",
                                "Teste",
                                "Teste",
                                "Teste",
                                tipoEndereco.RESIDENCIAL)))
                .build();

        Cliente clienteAntigo = getCliente();

        //when
        when(repository.existsById(email)).thenReturn(true);
        when(repository.findById(email)).thenReturn(Optional.of(clienteAntigo));

        ResponseEntity<String> expected = ResponseEntity.accepted().body("{\"Cadastro atualizado com sucesso.\"}");
        ResponseEntity<String> result = clienteService.atualizarCliente(clienteNovo, email);

        //then
        assertThat(result).isEqualTo(expected);
        verify(repository,times(1)).existsById(email);
        verify(repository,times(1)).save(clienteAntigo);

    }

//    @Test
//    void listarClientes() {
//
//        Cliente cliente1 = getCliente();
//        Cliente cliente2 = getCliente();
//        cliente1.setEmail("janny.doe@gmail.com");
//
//        when(repository.findAll()).thenReturn(List.of(cliente1, cliente2));
//
//        ResponseEntity<List<Cliente>> expected = ResponseEntity.ok().body(List.of(cliente1, cliente2));
//        ResponseEntity<List<Cliente>> result = clienteService.listarClientes();
//
//        assertThat(result).isEqualTo(expected);
//        verify(repository, times(1)).findAll();
//
//    }
}
